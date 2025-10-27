package it.planner.travel.service.excel;

import java.util.UUID;

import org.springframework.stereotype.Service;

import it.planner.travel.domain.dto.response.TravelFullResponseDto;
import it.planner.travel.exception.base.BaseException;
import it.planner.travel.service.TravelService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelService {

    // Service
    private final TravelService travelService;

    public String printExcel(UUID uuidTravel, String token) throws BaseException {
        // Recupero il travel in base allo UUID
        TravelFullResponseDto travelFullResponseDto = travelService.findByUuidAndUuidUser(uuidTravel, token);


        
        return null;

    }
}
