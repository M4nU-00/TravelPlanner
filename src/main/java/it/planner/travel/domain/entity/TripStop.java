package it.planner.travel.domain.entity;

import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "name_city")
    private String nameCity;

    @ManyToOne
    @JoinColumn(name = "travel_uuid")
    @JsonIgnore
    private Travel travel;

    @Column(name = "name")
    private String name;

    @Column(name = "trip_stop_date")
    private LocalDate tripStopDate;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "tripStop")
    @JsonIgnore
    List<InterestPoint> interestPointList;

}
