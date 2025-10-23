package it.planner.travel.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.planner.travel.domain.dto.request.TripStopRequestDto;
import it.planner.travel.domain.dto.response.TripStopResponseDto;
import it.planner.travel.exception.base.BaseException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public interface TripStopService {

    public TripStopResponseDto createTripStop(TripStopRequestDto TripStopRequestDto) throws BaseException;

    public TripStopResponseDto findByUuid(UUID uuid) throws BaseException;

    public List<TripStopResponseDto> findAll();

    public TripStopResponseDto updateTripStop(UUID uuid, TripStopRequestDto TripStopRequestDto) throws BaseException;

    public void deleteTripStop(UUID uuid) throws BaseException;

}
