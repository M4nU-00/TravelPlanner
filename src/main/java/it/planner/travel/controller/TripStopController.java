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

import it.planner.travel.domain.dto.request.TripStopRequestDto;
import it.planner.travel.domain.dto.response.TripStopResponseDto;
import it.planner.travel.exception.base.BaseException;
import it.planner.travel.service.TripStopService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trip-stop")
public class TripStopController {

    private final TripStopService tripStopService;

    @PostMapping
    public TripStopResponseDto createTripStop(@RequestBody TripStopRequestDto tripStopRequestDto) throws BaseException {
        return tripStopService.createTripStop(tripStopRequestDto);
    }

    @GetMapping("/{uuid}")
    public TripStopResponseDto getTripStopByUuid(@PathVariable UUID uuid) throws BaseException {
        return tripStopService.findByUuid(uuid);
    }

    @GetMapping
    public List<TripStopResponseDto> getAllTripStops() {
        return tripStopService.findAll();
    }

    @PutMapping("/{uuid}")
    public TripStopResponseDto updateTripStop(@PathVariable UUID uuid,
                                              @RequestBody TripStopRequestDto tripStopRequestDto) throws BaseException {
        return tripStopService.updateTripStop(uuid, tripStopRequestDto);
    }

    @DeleteMapping("/{uuid}")
    public void deleteTripStop(@PathVariable UUID uuid) throws BaseException {
        tripStopService.deleteTripStop(uuid);
    }
}
