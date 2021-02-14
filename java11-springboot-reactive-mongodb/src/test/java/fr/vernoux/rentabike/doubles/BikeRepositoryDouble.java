package fr.vernoux.rentabike.doubles;

import fr.vernoux.rentabike.domain.bike.*;
import fr.vernoux.rentabike.domain.standard.Zone;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class BikeRepositoryDouble implements BikeRepository {

    private final Map<BikeId, Bike> database = new HashMap<>();

    @Override
    public Mono<Void> save(Bike bike) {
        return Mono.create(s -> {
            database.put(bike.getId(), bike);
            s.success();
        });
    }

    @Override
    public Mono<Bike> getById(BikeId id) {
        return Mono.fromCallable(() -> database.get(id)).switchIfEmpty(Mono.error(new UnknownBikeException()));
    }

    @Override
    public Flux<Bike> findAllByStatusInZone(BikeStatus status, Zone zone) {
        return Flux.defer(() -> Flux.fromIterable(database.values()))
                .filter(bike -> status.equals(bike.getStatus()));
    }

    @Override
    public Mono<Void> deleteAll() {
        return Mono.create(s -> {
            database.clear();
            s.success();
        });
    }
}
