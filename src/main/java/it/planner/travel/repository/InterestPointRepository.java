package it.planner.travel.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.planner.travel.domain.entity.InterestPoint;

@Repository
public interface InterestPointRepository extends JpaRepository<InterestPoint, UUID> {

    // FindAll degli elementi non cancellati
    List<InterestPoint> findAllByDeleteDateIsNull();

    // FindById degli elementi non cancellati
    Optional<InterestPoint> findByUuidAndDeleteDateIsNull(UUID uuid);

}
