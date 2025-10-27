package it.planner.travel.service.excel;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.planner.travel.domain.dto.response.InterestPointResponseDto;
import it.planner.travel.domain.dto.response.TravelFullResponseDto;
import it.planner.travel.domain.dto.response.TripStopResponseDto;
import it.planner.travel.exception.base.BaseException;
import it.planner.travel.service.TravelService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelService {

    // Service
    private final TravelService travelService;

    public ResponseEntity<ByteArrayResource> printExcel(UUID uuidTravel, String token) throws BaseException {
        // Recupero il travel in base allo UUID
        TravelFullResponseDto travelFullResponseDto = travelService.findByUuidAndUuidUser(uuidTravel, token);

        return generateExcelFromTravel(travelFullResponseDto);

    }

    private ResponseEntity<ByteArrayResource> generateExcelFromTravel(TravelFullResponseDto travelDto) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Travel Itinerary");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            int rowIdx = 0;

            // Travel header
            Row headerRow = sheet.createRow(rowIdx++);
            headerRow.createCell(0).setCellValue("Travel Name");
            headerRow.createCell(1).setCellValue("Start Date");
            headerRow.createCell(2).setCellValue("End Date");

            Row travelRow = sheet.createRow(rowIdx++);
            travelRow.createCell(0).setCellValue(travelDto.getName());
            travelRow.createCell(1).setCellValue(travelDto.getStartDate().format(formatter));
            travelRow.createCell(2).setCellValue(travelDto.getEndDate().format(formatter));

            rowIdx++; // empty row

            // Trip stop header
            Row stopHeader = sheet.createRow(rowIdx++);
            stopHeader.createCell(0).setCellValue("City");
            stopHeader.createCell(1).setCellValue("Trip Stop Name");
            stopHeader.createCell(2).setCellValue("Date");
            stopHeader.createCell(3).setCellValue("Note");
            stopHeader.createCell(4).setCellValue("Interest Point Name");

            for (TripStopResponseDto stop : travelDto.getTripStopResponseList()) {
                Row stopRow = sheet.createRow(rowIdx++);
                stopRow.createCell(0).setCellValue(stop.getNameCity());
                stopRow.createCell(1).setCellValue(stop.getNameTripStop());
                stopRow.createCell(2).setCellValue(stop.getTripStopDate().format(formatter));
                stopRow.createCell(3).setCellValue(stop.getNote());

                for (InterestPointResponseDto ip : stop.getInterestPointList()) {
                    Row ipRow = sheet.createRow(rowIdx++);
                    ipRow.createCell(4).setCellValue(ip.getName());
                }

                rowIdx++; // spacing between stops
            }

            // Auto-size columns
            for (int i = 0; i <= 4; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to byte array
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=travel_itinerary.xlsx")
                    .contentType(MediaType
                            .parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException("Errore nella generazione del file Excel", e);
        }
    }

}
