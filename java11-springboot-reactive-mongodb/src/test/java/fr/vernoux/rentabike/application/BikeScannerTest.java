package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.bike.UnknownBikeException;
import fr.vernoux.rentabike.domain.standard.Position;
import fr.vernoux.rentabike.doubles.BikeRepositoryDouble;
import fr.vernoux.rentabike.doubles.EventBusDouble;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class BikeScannerTest {

    @Test
    void scansTheBike() {
        BikeRepository bikeRepository = new BikeRepositoryDouble();
        EventBusDouble eventBus = new EventBusDouble();
        BikeScanner bikeScanner = new BikeScanner(bikeRepository, eventBus);
        BikeId bikeId = new BikeId("123");
        Position position = new Position(48d, 2d);
        Bike bike = new Bike(bikeId, position);
        bikeRepository.save(bike).block();

        StepVerifier.create(bikeScanner.scan(bikeId)).verifyComplete();

        StepVerifier.create(bikeRepository.getById(bikeId))
                .expectNextMatches(modifiedBike -> !modifiedBike.isAvailable())
                .verifyComplete();
        assertThat(eventBus.getEmittedEvents()).isNotEmpty();
    }

    @Test
    void rejectsUnknownBikeId() {
        BikeRepository bikeRepository = new BikeRepositoryDouble();
        EventBusDouble eventBus = new EventBusDouble();
        BikeScanner bikeScanner = new BikeScanner(bikeRepository, eventBus);

        StepVerifier.create(bikeScanner.scan(new BikeId("wrongId"))).verifyError(UnknownBikeException.class);
    }
}
