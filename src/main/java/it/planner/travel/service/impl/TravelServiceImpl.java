package it.planner.travel.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import it.planner.travel.domain.dto.request.TravelRequestDto;
import it.planner.travel.domain.dto.response.TravelResponseDto;
import it.planner.travel.domain.entity.Travel;
import it.planner.travel.exception.ObjectNotFoundException;
import it.planner.travel.exception.base.BaseException;
import it.planner.travel.repository.TravelRepository;
import it.planner.travel.service.TravelService;
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

    public TravelResponseDto createTravel(TravelRequestDto travelRequestDto) {
        return modelMapper
                .map(insert(modelMapper
                        .map(travelRequestDto, Travel.class)), TravelResponseDto.class);
    }

    public TravelResponseDto findByUuid(UUID uuid) throws BaseException {
        TravelResponseDto travelResponseDto = modelMapper
                .map(travelRepository.findByUuidAndDeleteDateIsNull(uuid)
                        .orElseThrow(() -> new ObjectNotFoundException("Travel", uuid)), TravelResponseDto.class);
        log.info("Oggetto trovato {}", travelResponseDto);
        return travelResponseDto;
    }

    public List<TravelResponseDto> findAll() {
        return travelRepository.findAllByDeleteDateIsNull()
                .stream()
                .map(ithTravelResponseDto -> {
                    return modelMapper
                            .map(ithTravelResponseDto, TravelResponseDto.class);
                }).toList();
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
