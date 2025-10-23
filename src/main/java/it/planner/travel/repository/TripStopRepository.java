package it.planner.travel.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.planner.travel.domain.entity.TripStop;

@Repository
public interface TripStopRepository extends JpaRepository<TripStop, UUID> {

    // FindAll degli elementi non cancellati
    List<TripStop> findAllByDeleteDateIsNull();

    // FindById degli elementi non cancellati
    Optional<TripStop> findByUuidAndDeleteDateIsNull(UUID uuid);

}
