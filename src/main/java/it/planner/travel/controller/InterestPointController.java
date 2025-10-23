package it.planner.travel.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.planner.travel.domain.dto.request.InterestPointRequestDto;
import it.planner.travel.domain.dto.response.InterestPointResponseDto;
import it.planner.travel.exception.base.BaseException;
import it.planner.travel.service.InterestPointService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/interest-point")
public class InterestPointController {

    private final InterestPointService interestPointService;

    @PostMapping
    public InterestPointResponseDto createInterestPoint(@RequestBody InterestPointRequestDto interestPointRequestDto)
            throws BaseException {
        return interestPointService.createInterestPoint(interestPointRequestDto);
    }

    @GetMapping("/{uuid}")
    public InterestPointResponseDto getInterestPointByUuid(@PathVariable UUID uuid) throws BaseException {
        return interestPointService.findByUuid(uuid);
    }

    @GetMapping
    public List<InterestPointResponseDto> getAllInterestPoints() {
        return interestPointService.findAll();
    }

    @PutMapping("/{uuid}")
    public InterestPointResponseDto updateInterestPoint(@PathVariable UUID uuid,
            @RequestBody InterestPointRequestDto interestPointRequestDto) throws BaseException {
        return interestPointService.updateInterestPoint(uuid, interestPointRequestDto);
    }

    @DeleteMapping("/{uuid}")
    public void deleteInterestPoint(@PathVariable UUID uuid) throws BaseException {
        interestPointService.deleteInterestPoint(uuid);
    }
}