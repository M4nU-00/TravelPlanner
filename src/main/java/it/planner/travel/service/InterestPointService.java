package it.planner.travel.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import it.planner.travel.domain.dto.request.InterestPointRequestDto;
import it.planner.travel.domain.dto.response.InterestPointResponseDto;
import it.planner.travel.exception.base.BaseException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public interface InterestPointService {

    public InterestPointResponseDto createInterestPoint(InterestPointRequestDto interestPointRequestDto) throws BaseException;

    public InterestPointResponseDto findByUuid(UUID uuid) throws BaseException;

    public List<InterestPointResponseDto> findAll();

    public InterestPointResponseDto updateInterestPoint(UUID uuid, InterestPointRequestDto interestPointRequestDto) throws BaseException;

    public void deleteInterestPoint(UUID uuid) throws BaseException;

}
