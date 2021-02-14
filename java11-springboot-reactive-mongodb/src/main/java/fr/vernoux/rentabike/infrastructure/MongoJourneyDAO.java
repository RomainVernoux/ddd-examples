package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public interface MongoJourneyDAO extends ReactiveMongoRepository<Journey, JourneyId> {

    Flux<Journey> findAllByBikeId(BikeId bikeId);
}
