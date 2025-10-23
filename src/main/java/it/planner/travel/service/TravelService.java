package it.planner.travel.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.planner.travel.domain.dto.request.TravelRequestDto;
import it.planner.travel.domain.dto.response.TravelFullResponseDto;
import it.planner.travel.domain.dto.response.TravelResponseDto;
import it.planner.travel.exception.base.BaseException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public interface TravelService {

    public TravelResponseDto createTravel(TravelRequestDto travelRequestDto);

    public TravelResponseDto findByUuid(UUID uuid) throws BaseException;

    public List<TravelFullResponseDto> findAll();

    public TravelResponseDto updateTravel(UUID uuid, TravelRequestDto travelRequestDto) throws BaseException;

    public void deleteTravel(UUID uuid) throws BaseException;

}
