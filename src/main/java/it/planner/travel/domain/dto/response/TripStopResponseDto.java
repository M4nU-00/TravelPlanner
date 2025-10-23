package it.planner.travel.domain.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class TripStopResponseDto {

    UUID uuidTripStop;
    String nameTripStop;
    String nameCity;
    LocalDateTime tripStopDate;
    String note;
    UUID uuidTravel;
}
