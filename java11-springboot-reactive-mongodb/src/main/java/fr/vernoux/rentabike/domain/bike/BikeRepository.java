package fr.vernoux.rentabike.domain.bike;

import fr.vernoux.rentabike.domain.standard.Zone;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BikeRepository {

    Mono<Void> save(Bike bike);

    Mono<Bike> getById(BikeId id);

    Flux<Bike> findAllByStatusInZone(BikeStatus status, Zone zone);

    Mono<Void> deleteAll();
}
