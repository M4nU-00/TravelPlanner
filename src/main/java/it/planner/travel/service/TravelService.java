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

    public TravelResponseDto createTravel(TravelRequestDto travelRequestDto, String token);

    public TravelFullResponseDto findByUuidAndUuidUser(UUID uuid, String token) throws BaseException;

    public List<TravelFullResponseDto> findAllByUuidUser(String token) throws BaseException;

    public TravelResponseDto updateTravel(UUID uuid, TravelRequestDto travelRequestDto, String token)
            throws BaseException;

    public void deleteTravel(UUID uuid, String token) throws BaseException;

}
