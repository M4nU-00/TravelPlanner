package it.planner.travel.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import it.planner.travel.domain.dto.request.TripStopRequestDto;
import it.planner.travel.domain.dto.response.TripStopResponseDto;
import it.planner.travel.domain.entity.Travel;
import it.planner.travel.domain.entity.TripStop;
import it.planner.travel.exception.ObjectNotFoundException;
import it.planner.travel.exception.base.BaseException;
import it.planner.travel.repository.TripStopRepository;
import it.planner.travel.service.TravelService;
import it.planner.travel.service.TripStopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripStopServiceImpl implements TripStopService {

    // Repository
    private final TripStopRepository tripStopRepository;

    // Service
    private final TravelService travelService;

    // ModelMapper
    private final ModelMapper modelMapper;

    @Override
    public TripStopResponseDto createTripStop(TripStopRequestDto tripStopRequestDto) throws BaseException {

        // Fare una chiamata esterna per verificare se esiste la città passata
        String nameCity = tripStopRequestDto.getNameCity();

        // Verifico se il travel passato esiste
        Travel travel = modelMapper.map(travelService.findByUuid(tripStopRequestDto.getUuidTravel()), Travel.class);

        // Creo l'oggetto TripStop
        TripStop tripStop = TripStop
                .builder()
                .name(tripStopRequestDto.getNameTripStop())
                .nameCity(nameCity)
                .note(tripStopRequestDto.getNote())
                .travel(travel)
                .tripStopDate(tripStopRequestDto.getTripStopDate())
                .build();

        return modelMapper
                .map(insert(tripStop), TripStopResponseDto.class);
    }

    @Override
    public TripStopResponseDto findByUuid(UUID uuid) throws BaseException {
        TripStopResponseDto tripStopResponseDto = modelMapper
                .map(tripStopRepository.findByUuidAndDeleteDateIsNull(uuid)
                        .orElseThrow(() -> new ObjectNotFoundException("TripStop", uuid)), TripStopResponseDto.class);
        log.info("Oggetto trovato {}", tripStopResponseDto);
        return tripStopResponseDto;
    }

    @Override
    public List<TripStopResponseDto> findAll() {
        return tripStopRepository.findAllByDeleteDateIsNull()
                .stream()
                .map(ithTripStopResponseDto -> {
                    return modelMapper
                            .map(ithTripStopResponseDto, TripStopResponseDto.class);
                }).toList();
    }

    @Override
    public TripStopResponseDto updateTripStop(UUID uuid, TripStopRequestDto tripStopRequestDto) throws BaseException {
        TripStop tripStop = modelMapper.map(findByUuid(uuid), TripStop.class);

        // Aggiorna i campi
        tripStop.setName(tripStopRequestDto.getNameTripStop());

        // Fare chiamata al servizio esterno per recuperare l'oggetto City
        // Ora la setto di default
        tripStop.setNameCity(tripStopRequestDto.getNameCity());
        tripStop.setNote(tripStopRequestDto.getNote());
        tripStop.setTripStopDate(tripStopRequestDto.getTripStopDate());

        // Verifico se esiste il travel
        Travel travel = modelMapper.map(travelService.findByUuid(tripStopRequestDto.getUuidTravel()), Travel.class);
        tripStop.setTravel(travel);

        // Aggiorno la data
        tripStop.onUpdate();

        // Mappa l'entità aggiornata nel DTO di risposta
        return modelMapper.map(insert(tripStop), TripStopResponseDto.class);
    }

    @Override
    public void deleteTripStop(UUID uuid) throws BaseException {
        TripStop TripStop = modelMapper.map(findByUuid(uuid), TripStop.class);
        TripStop.softDelete();
        log.info("Oggetto eliminato logicamente {}");
    }

    // Metodi Private
    private TripStop insert(TripStop TripStop) {
        TripStop = tripStopRepository.save(TripStop);
        log.info("Inserimento nuova tappa {}", TripStop);
        return TripStop;
    }

}
