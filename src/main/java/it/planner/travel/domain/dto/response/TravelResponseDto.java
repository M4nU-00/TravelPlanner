package it.planner.travel.domain.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
public class TravelResponseDto {

    private UUID uuid;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
