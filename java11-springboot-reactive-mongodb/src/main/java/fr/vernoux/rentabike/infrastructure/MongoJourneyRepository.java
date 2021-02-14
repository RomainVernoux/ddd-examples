package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MongoJourneyRepository implements JourneyRepository {

    private final MongoJourneyDAO dao;

    public MongoJourneyRepository(MongoJourneyDAO dao) {
        this.dao = dao;
    }

    @Override
    public Mono<Void> save(Journey journey) {
        return dao.save(journey).then();
    }

    @Override
    public Flux<Journey> findAll() {
        return dao.findAll();
    }

    @Override
    public Flux<Journey> findAllByBike(BikeId bikeId) {
        return dao.findAllByBikeId(bikeId);
    }

    @Override
    public Mono<Void> deleteAll() {
        return dao.deleteAll();
    }
}
