package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MongoJourneyDAO extends MongoRepository<Journey, JourneyId> {

    List<Journey> findAllByBikeId(BikeId bikeId);
}
