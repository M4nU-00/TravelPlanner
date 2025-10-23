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

import it.planner.travel.domain.dto.request.TravelRequestDto;
import it.planner.travel.domain.dto.response.TravelFullResponseDto;
import it.planner.travel.domain.dto.response.TravelResponseDto;
import it.planner.travel.exception.base.BaseException;
import it.planner.travel.service.TravelService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/travel")
public class TravelController {

    private final TravelService travelService;

    @PostMapping
    public TravelResponseDto createTravel(@RequestBody TravelRequestDto travelRequestDto) {
        return travelService.createTravel(travelRequestDto);
    }

    @GetMapping("/{uuid}")
    public TravelResponseDto getTravelByUuid(@PathVariable UUID uuid) throws BaseException {
        return travelService.findByUuid(uuid);
    }

    @GetMapping
    public List<TravelFullResponseDto> getAllTravels() {
        return travelService.findAll();
    }

    @PutMapping("/{uuid}")
    public TravelResponseDto updateTravel(@PathVariable UUID uuid,
                                          @RequestBody TravelRequestDto travelRequestDto) throws BaseException {
        return travelService.updateTravel(uuid, travelRequestDto);
    }

    @DeleteMapping("/{uuid}")
    public void deleteTravel(@PathVariable UUID uuid) throws BaseException {
        travelService.deleteTravel(uuid);
    }
}
