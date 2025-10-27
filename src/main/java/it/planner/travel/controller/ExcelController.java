package it.planner.travel.controller;

import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.planner.travel.exception.base.BaseException;
import it.planner.travel.service.excel.ExcelService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/excel")
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("/download/{uuid}")
    public ResponseEntity<ByteArrayResource> downloadExcel(@PathVariable UUID uuid, @RequestHeader("Authorization") String authHeader)
            throws BaseException {
        String token = authHeader.replace("Bearer ", "");
        return excelService.printExcel(uuid, token);
    }

}
