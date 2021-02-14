package fr.vernoux.rentabike.domain.journey;

import fr.vernoux.rentabike.domain.bike.BikeId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface JourneyRepository {

    Mono<Void> save(Journey journey);

    Flux<Journey> findAll();

    Flux<Journey> findAllByBike(BikeId bikeId);

    Mono<Void> deleteAll();
}
