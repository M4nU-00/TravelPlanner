package it.planner.travel.domain.dto.response;

import java.time.LocalDate;
import java.util.List;
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
public class TravelFullResponseDto {
    
    
    private UUID uuid;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<TripStopResponseDto> tripStopResponseList;
}
