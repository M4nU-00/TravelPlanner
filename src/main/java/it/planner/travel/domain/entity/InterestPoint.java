package it.planner.travel.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.planner.travel.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "interest_point")
public class InterestPoint extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "trip_stop_uuid")
    @JsonIgnore
    private TripStop tripStop;

    @Column(name = "name")
    private String name;

}
