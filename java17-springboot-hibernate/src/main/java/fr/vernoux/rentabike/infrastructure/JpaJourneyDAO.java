package fr.vernoux.rentabike.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface JpaJourneyDAO extends JpaRepository<JpaJourney, UUID> {

    List<JpaJourney> findAllByBikeId(UUID bikeId);
}
