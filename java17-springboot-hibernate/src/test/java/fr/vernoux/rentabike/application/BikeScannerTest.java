package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.UnknownBikeException;
import fr.vernoux.rentabike.doubles.BikeRepositoryDouble;
import fr.vernoux.rentabike.doubles.EventBusDouble;
import org.junit.jupiter.api.Test;

import static fr.vernoux.rentabike.utils.TestUtils.aRandomId;
import static fr.vernoux.rentabike.utils.TestUtils.aRandomPosition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BikeScannerTest {

    @Test
    void scansTheBike() throws UnknownBikeException {
        var bikeRepository = new BikeRepositoryDouble();
        var eventBus = new EventBusDouble();
        var bikeScanner = new BikeScanner(bikeRepository, eventBus);
        var bikeId = new BikeId(aRandomId());
        var bike = Bike.enroll(bikeId, aRandomPosition());
        bikeRepository.save(bike);

        bikeScanner.scan(bikeId);

        var modifiedBike = bikeRepository.getById(bikeId);
        assertThat(modifiedBike.isAvailable()).isFalse();
        assertThat(eventBus.getEmittedEvents()).isNotEmpty();
    }

    @Test
    void rejectsUnknownBikeId() {
        var bikeRepository = new BikeRepositoryDouble();
        var eventBus = new EventBusDouble();
        var bikeScanner = new BikeScanner(bikeRepository, eventBus);

        var unknownBikeId = new BikeId(aRandomId());
        assertThatThrownBy(() -> bikeScanner.scan(unknownBikeId))
                .isInstanceOf(UnknownBikeException.class);
    }
}
