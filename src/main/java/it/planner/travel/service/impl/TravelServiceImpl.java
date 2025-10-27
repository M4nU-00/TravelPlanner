package it.planner.travel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import it.planner.travel.domain.dto.UserResponseDto;
import it.planner.travel.domain.dto.request.TravelRequestDto;
import it.planner.travel.domain.dto.response.InterestPointResponseDto;
import it.planner.travel.domain.dto.response.TravelFullResponseDto;
import it.planner.travel.domain.dto.response.TravelResponseDto;
import it.planner.travel.domain.dto.response.TripStopResponseDto;
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
    public TravelFullResponseDto findByUuidAndUuidUser(UUID uuid, String token) throws BaseException {
        // Verifico se l'utente esiste
        UUID uuidUser = userService.getUserProfile(token) != null ? userService.getUserProfile(token).getUuidUser()
                : null;

        if (uuidUser == null) {
            // Lanciare un eccezione
        }

        Travel travel = travelRepository.findByUuidAndUuidUserAndDeleteDateIsNull(uuid, uuidUser)
                .orElseThrow(() -> new ObjectNotFoundException("Travel", uuid));

        TravelFullResponseDto dto = buildTravelFullResponseDto(travel);
        log.info("Oggetto trovato {}", dto);
        return dto;
    }

    @Override
    public List<TravelFullResponseDto> findAllByUuidUser(String token) throws BaseException {
        UUID uuidUser = userService.getUserProfile(token) != null ? userService.getUserProfile(token).getUuidUser()
                : null;

        if (uuidUser == null) {
            // Lanciare un eccezione
        }
        List<Travel> travels = travelRepository.findAllByUuidUserAndDeleteDateIsNull(uuidUser);
        return travels.stream()
                .map(this::buildTravelFullResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TravelResponseDto updateTravel(UUID uuid, TravelRequestDto travelRequestDto, String token)
            throws BaseException {
        Travel travel = modelMapper.map(findByUuidAndUuidUser(uuid, token), Travel.class);

        // Aggiorna i campi
        travel.setName(travelRequestDto.getName());
        travel.setStartDate(travelRequestDto.getStartDate());
        travel.setEndDate(travelRequestDto.getEndDate());
        travel.onUpdate();

        // Mappa l'entità aggiornata nel DTO di risposta
        return modelMapper.map(insert(travel), TravelResponseDto.class);
    }

    @Override
    public void deleteTravel(UUID uuid, String token) throws BaseException {
        Travel travel = modelMapper.map(findByUuidAndUuidUser(uuid, token), Travel.class);
        travel.softDelete();
        log.info("Oggetto eliminato logicamente {}");
    }

    // Metodi Private
    private Travel insert(Travel travel) {
        travel = travelRepository.save(travel);
        log.info("Inserimento nuovo viaggio {}", travel);
        return travel;
    }

    private TravelFullResponseDto buildTravelFullResponseDto(Travel travel) {
        TravelFullResponseDto dto = new TravelFullResponseDto();
        dto.setName(travel.getName());
        dto.setStartDate(travel.getStartDate());
        dto.setEndDate(travel.getEndDate());
        dto.setUuid(travel.getUuid());

        List<TripStopResponseDto> tripStopDtos = new ArrayList<>();
        for (TripStop tripStop : travel.getTripStopList()) {
            TripStopResponseDto tripStopDto = new TripStopResponseDto();
            tripStopDto.setNameCity(tripStop.getNameCity());
            tripStopDto.setNameTripStop(tripStop.getName());
            tripStopDto.setUuidTravel(travel.getUuid());
            tripStopDto.setUuidTripStop(tripStop.getUuid());
            tripStopDto.setTripStopDate(tripStop.getTripStopDate());
            tripStopDto.setNote(tripStop.getNote());

            List<InterestPointResponseDto> interestPointDtos = tripStop.getInterestPointList().stream()
                    .map(interestPointDto -> {
                        InterestPointResponseDto ipDto = new InterestPointResponseDto();
                        ipDto.setName(interestPointDto.getName());
                        return ipDto;
                    })
                    .collect(Collectors.toList());

            tripStopDto.setInterestPointList(interestPointDtos);
            tripStopDtos.add(tripStopDto);
        }

        dto.setTripStopResponseList(tripStopDtos);
        return dto;
    }

}
