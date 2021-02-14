package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.EventBus;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import reactor.core.publisher.Mono;

import static java.util.Objects.requireNonNull;

public class BikeScanner {

    private final BikeRepository bikeRepository;
    private final EventBus eventBus;

    public BikeScanner(BikeRepository bikeRepository, EventBus eventBus) {
        this.bikeRepository = bikeRepository;
        this.eventBus = eventBus;
    }

    public Mono<Void> scan(BikeId bikeId) {
        requireNonNull(bikeId);
        return bikeRepository.getById(bikeId)
                .map(bike -> MutationResult.of(bike, bike.scan()))
                .flatMap(result -> bikeRepository.save(result.entity()).thenReturn(result))
                .flatMap(result -> eventBus.emitAll(result.events()).thenReturn(result))
                .then();
    }
}
