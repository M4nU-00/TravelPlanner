package it.planner.travel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import it.planner.travel.domain.dto.UserResponseDto;
import it.planner.travel.domain.dto.request.TravelRequestDto;
import it.planner.travel.domain.dto.response.InterestPointResponseDto;
import it.planner.travel.domain.dto.response.TravelFullResponseDto;
import it.planner.travel.domain.dto.response.TravelResponseDto;
import it.planner.travel.domain.dto.response.TripStopResponseDto;
import it.planner.travel.domain.entity.InterestPoint;
import it.planner.travel.domain.entity.Travel;
import it.planner.travel.domain.entity.TripStop;
import it.planner.travel.domain.util.JwtUtil;
import it.planner.travel.exception.ObjectNotFoundException;
import it.planner.travel.exception.base.BaseException;
import it.planner.travel.repository.TravelRepository;
import it.planner.travel.service.TravelService;
import it.planner.travel.service.restservice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelServiceImpl implements TravelService {

    // Repository
    private final TravelRepository travelRepository;

    // ModelMapper
    private final ModelMapper modelMapper;

    // User Service esterno
    private final UserService userService;

    // JwtUtil
    private final JwtUtil jwtUtil;

    @Override
    public TravelResponseDto createTravel(TravelRequestDto travelRequestDto, String token) {

        // Check UserService
        String username = jwtUtil.extractUsername(token);

        UserResponseDto userResponseDto = userService.getUserProfile(token);

        if (!username.equals(userResponseDto.getUsername())) {
            // errore
        }

        Travel travel = Travel.builder()
                .startDate(travelRequestDto.getStartDate())
                .name(travelRequestDto.getName())
                .endDate(travelRequestDto.getEndDate())
                .uuidUser(userResponseDto.getUuidUser())
                .build();

        travel = insert(travel);

        return modelMapper
                .map(travel, TravelResponseDto.class);
    }

    @Override
    public TravelResponseDto findByUuid(UUID uuid) throws BaseException {
        TravelResponseDto travelResponseDto = modelMapper
                .map(travelRepository.findByUuidAndDeleteDateIsNull(uuid)
                        .orElseThrow(() -> new ObjectNotFoundException("Travel", uuid)), TravelResponseDto.class);
        log.info("Oggetto trovato {}", travelResponseDto);
        return travelResponseDto;
    }

    @Override
    public List<TravelFullResponseDto> findAll() {
        List<Travel> travels = travelRepository.findAllByDeleteDateIsNull();
        List<TravelFullResponseDto> travelFullResponseDtos = new ArrayList<>();

        for (Travel ithTravel : travels) {
            TravelFullResponseDto travelFullResponseDto = new TravelFullResponseDto();
            travelFullResponseDto.setName(ithTravel.getName());
            travelFullResponseDto.setStartDate(ithTravel.getStartDate());
            travelFullResponseDto.setEndDate(ithTravel.getEndDate());
            travelFullResponseDto.setUuid(ithTravel.getUuid());

            List<TripStopResponseDto> tripStopResponseDtos = new ArrayList<>();

            for (TripStop ithTripStop : ithTravel.getTripStopList()) {
                TripStopResponseDto tripStopResponseDto = new TripStopResponseDto();
                tripStopResponseDto.setNameCity(ithTripStop.getNameCity());
                tripStopResponseDto.setNameTripStop(ithTripStop.getName());
                tripStopResponseDto.setUuidTravel(ithTravel.getUuid());
                tripStopResponseDto.setUuidTripStop(ithTripStop.getUuid());
                tripStopResponseDto.setTripStopDate(ithTripStop.getTripStopDate());
                tripStopResponseDto.setNote(ithTripStop.getNote());

                List<InterestPointResponseDto> interestPointResponseDtos = new ArrayList<>();

                for (InterestPoint ithInterestPoint : ithTripStop.getInterestPointList()) {
                    InterestPointResponseDto interestPointResponseDto = new InterestPointResponseDto();
                    interestPointResponseDto.setName(ithInterestPoint.getName());
                    interestPointResponseDtos.add(interestPointResponseDto);
                }

                tripStopResponseDto.setInterestPointList(interestPointResponseDtos);
                tripStopResponseDtos.add(tripStopResponseDto);
            }

            travelFullResponseDto.setTripStopResponseList(tripStopResponseDtos);
            travelFullResponseDtos.add(travelFullResponseDto);
        }

        return travelFullResponseDtos;
    }

    @Override
    public TravelResponseDto updateTravel(UUID uuid, TravelRequestDto travelRequestDto) throws BaseException {
        Travel travel = modelMapper.map(findByUuid(uuid), Travel.class);

        // Aggiorna i campi
        travel.setName(travelRequestDto.getName());
        travel.setStartDate(travelRequestDto.getStartDate());
        travel.setEndDate(travelRequestDto.getEndDate());
        travel.onUpdate();

        // Mappa l'entità aggiornata nel DTO di risposta
        return modelMapper.map(insert(travel), TravelResponseDto.class);
    }

    @Override
    public void deleteTravel(UUID uuid) throws BaseException {
        Travel travel = modelMapper.map(findByUuid(uuid), Travel.class);
        travel.softDelete();
        log.info("Oggetto eliminato logicamente {}");
    }

    // Metodi Private
    private Travel insert(Travel travel) {
        travel = travelRepository.save(travel);
        log.info("Inserimento nuovo viaggio {}", travel);
        return travel;
    }

}
