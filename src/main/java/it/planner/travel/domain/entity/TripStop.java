package it.planner.travel.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.planner.travel.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "trip_stop")
public class TripStop extends BaseEntity {

    @Column(name = "uuid_city")
    private UUID uuidCity;

    @ManyToOne
    @JoinColumn(name = "uuid_travel")
    @JsonIgnore
    private Travel travel;

    @Column(name = "name")
    private String name;

    @Column(name = "trip_stop_date")
    private LocalDateTime tripStopDate;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "tripStop")
    @JsonIgnore
    List<InterestPoint> interestPointList;

}
