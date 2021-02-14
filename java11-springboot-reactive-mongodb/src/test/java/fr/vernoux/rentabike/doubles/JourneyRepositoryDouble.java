package fr.vernoux.rentabike.doubles;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class JourneyRepositoryDouble implements JourneyRepository {

    private final Map<JourneyId, Journey> database = new HashMap<>();

    @Override
    public Mono<Void> save(Journey bike) {
        return Mono.create(s -> {
            database.put(bike.getId(), bike);
            s.success();
        });
    }

    @Override
    public Flux<Journey> findAll() {
        return Flux.defer(() -> Flux.fromIterable(database.values()));
    }

    @Override
    public Flux<Journey> findAllByBike(BikeId bikeId) {
        return findAll()
                .filter(journey -> journey.getBikeId().equals(bikeId));
    }

    @Override
    public Mono<Void> deleteAll() {
        return Mono.create(s -> {
            database.clear();
            s.success();
        });
    }
}
