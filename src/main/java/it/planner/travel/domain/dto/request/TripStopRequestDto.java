package it.planner.travel.domain.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
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
public class TripStopRequestDto {

    @NotNull(message = "uuidTravel must not be null")
    UUID uuidTravel;

    @NotNull(message = "nameTripStop must not be null")
    String nameTripStop;

    @NotNull(message = "nameCity must not be null")
    String nameCity;

    @NotNull(message = "tripStopDate must not be null")
    LocalDate tripStopDate;

    String note;

}
