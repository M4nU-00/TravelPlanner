package it.planner.travel.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.planner.travel.domain.entity.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel, UUID> {

    // FindAll degli elementi non cancellati
    List<Travel> findAllByUuidUserAndDeleteDateIsNull(UUID uuidUser);

    // FindById degli elementi non cancellati
    Optional<Travel> findByUuidAndUuidUserAndDeleteDateIsNull(UUID uuid, UUID uuidUser);

}
