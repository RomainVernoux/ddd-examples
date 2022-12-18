package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.EventBus;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.bike.UnknownBikeException;
import jakarta.transaction.Transactional;

import static java.util.Objects.requireNonNull;

public class BikeScanner {

    private final BikeRepository bikeRepository;
    private final EventBus eventBus;

    public BikeScanner(BikeRepository bikeRepository, EventBus eventBus) {
        this.bikeRepository = bikeRepository;
        this.eventBus = eventBus;
    }

    @Transactional
    public void scan(BikeId bikeId) throws UnknownBikeException {
        requireNonNull(bikeId);
        var bike = bikeRepository.getById(bikeId);
        var events = bike.scan();
        bikeRepository.save(bike);
        eventBus.emitAll(events);
    }
}
