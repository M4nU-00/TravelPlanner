package it.planner.travel.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import it.planner.travel.domain.dto.request.InterestPointRequestDto;
import it.planner.travel.domain.dto.response.InterestPointResponseDto;
import it.planner.travel.domain.entity.InterestPoint;
import it.planner.travel.domain.entity.TripStop;
import it.planner.travel.exception.ObjectNotFoundException;
import it.planner.travel.exception.base.BaseException;
import it.planner.travel.repository.InterestPointRepository;
import it.planner.travel.service.InterestPointService;
import it.planner.travel.service.TripStopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterestPointServiceImpl implements InterestPointService {

    // Repository
    private final InterestPointRepository interestPointRepository;

    // Service
    private final TripStopService tripStopService;

    // ModelMapper
    private final ModelMapper modelMapper;

    @Override
    public InterestPointResponseDto createInterestPoint(InterestPointRequestDto interestPointRequestDto)
            throws BaseException {

        // Fare la chiamata a TripStop per vedere se esiste
        TripStop tripstop = modelMapper.map(tripStopService.findByUuid(interestPointRequestDto.getUuidTripStop()),
                TripStop.class);

        InterestPoint interestPoint = InterestPoint.builder()
                .tripStop(tripstop)
                .name(interestPointRequestDto.getNameInterestPoint())
                .build();

        return modelMapper
                .map(insert(interestPoint), InterestPointResponseDto.class);
    }

    @Override
    public InterestPointResponseDto findByUuid(UUID uuid) throws BaseException {
        InterestPointResponseDto interestPointResponseDto = modelMapper
                .map(interestPointRepository.findByUuidAndDeleteDateIsNull(uuid)
                        .orElseThrow(() -> new ObjectNotFoundException("InterestPoint", uuid)),
                        InterestPointResponseDto.class);
        log.info("Oggetto trovato {}", interestPointResponseDto);
        return interestPointResponseDto;
    }

    @Override
    public List<InterestPointResponseDto> findAll() {
        return interestPointRepository.findAllByDeleteDateIsNull()
                .stream()
                .map(ithTripStopResponseDto -> {
                    return modelMapper
                            .map(ithTripStopResponseDto, InterestPointResponseDto.class);
                }).toList();
    }

    @Override
    public InterestPointResponseDto updateInterestPoint(UUID uuid, InterestPointRequestDto interestPointRequestDto)
            throws BaseException {

        // Fare la chiamata a TripStop per vedere se esiste
        TripStop tripstop = modelMapper.map(tripStopService.findByUuid(interestPointRequestDto.getUuidTripStop()),
                TripStop.class);

        // Verifico se lo uuid passato è corretto
        InterestPoint interestPoint = modelMapper.map(findByUuid(uuid), InterestPoint.class);

        // Aggiorna i campi
        interestPoint.setName(interestPointRequestDto.getNameInterestPoint());
        interestPoint.setTripStop(tripstop);

        // Aggiorno la data
        interestPoint.onUpdate();

        // Mappa l'entità aggiornata nel DTO di risposta
        return modelMapper.map(insert(interestPoint), InterestPointResponseDto.class);
    }

    @Override
    public void deleteInterestPoint(UUID uuid) throws BaseException {
        InterestPoint InterestPoint = modelMapper.map(findByUuid(uuid), InterestPoint.class);
        InterestPoint.softDelete();
        log.info("Oggetto eliminato logicamente {}");
    }

    // Metodi Private
    private InterestPoint insert(InterestPoint InterestPoint) {
        InterestPoint = interestPointRepository.save(InterestPoint);
        log.info("Inserimento nuovo Punto di interesse {}", InterestPoint);
        return InterestPoint;
    }

}
