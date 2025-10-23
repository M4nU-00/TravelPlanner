package it.planner.travel.domain.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
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
public class InterestPointRequestDto {

    @NotBlank(message = "uuidTravel must not be blank")
    UUID uuidTravel;

    @NotBlank(message = "uuidTripStop must not be blank")
    UUID uuidTripStop;

    @NotBlank(message = "nameInterestPoint must not be blank")
    String nameInterestPoint;

}
