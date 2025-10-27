package it.planner.travel.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public TravelResponseDto createTravel(@RequestBody TravelRequestDto travelRequestDto,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return travelService.createTravel(travelRequestDto, token);
    }

    @GetMapping("/{uuid}")
    public TravelFullResponseDto getTravelByUuid(@PathVariable UUID uuid, @RequestHeader("Authorization") String authHeader)
            throws BaseException {
        String token = authHeader.replace("Bearer ", "");
        return travelService.findByUuidAndUuidUser(uuid, token);
    }

    @GetMapping
    public List<TravelFullResponseDto> getAllTravels(@RequestHeader("Authorization") String authHeader)
            throws BaseException {
        String token = authHeader.replace("Bearer ", "");
        return travelService.findAllByUuidUser(token);
    }

    @PutMapping("/{uuid}")
    public TravelResponseDto updateTravel(@PathVariable UUID uuid,
            @RequestBody TravelRequestDto travelRequestDto, @RequestHeader("Authorization") String authHeader)
            throws BaseException {
        String token = authHeader.replace("Bearer ", "");
        return travelService.updateTravel(uuid, travelRequestDto, token);
    }

    @DeleteMapping("/{uuid}")
    public void deleteTravel(@PathVariable UUID uuid, @RequestHeader("Authorization") String authHeader)
            throws BaseException {
        String token = authHeader.replace("Bearer ", "");
        travelService.deleteTravel(uuid, token);
    }
}
