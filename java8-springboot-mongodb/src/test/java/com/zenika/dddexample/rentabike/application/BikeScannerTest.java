package com.zenika.dddexample.rentabike.application;

import com.zenika.dddexample.rentabike.domain.bike.Bike;
import com.zenika.dddexample.rentabike.domain.bike.BikeId;
import com.zenika.dddexample.rentabike.domain.bike.BikeRepository;
import com.zenika.dddexample.rentabike.domain.bike.UnknownBikeException;
import com.zenika.dddexample.rentabike.domain.standard.Position;
import com.zenika.dddexample.rentabike.doubles.BikeRepositoryDouble;
import com.zenika.dddexample.rentabike.doubles.EventBusDouble;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BikeScannerTest {

    @Test
    void scansTheBike() throws UnknownBikeException {
        BikeRepository bikeRepository = new BikeRepositoryDouble();
        EventBusDouble eventBus = new EventBusDouble();
        BikeScanner bikeScanner = new BikeScanner(bikeRepository, eventBus);
        BikeId bikeId = new BikeId("123");
        Position position = new Position(48d, 2d);
        Bike bike = new Bike(bikeId, position);
        bikeRepository.save(bike);

        bikeScanner.scan(bikeId);

        Bike modifiedBike = bikeRepository.getById(bikeId);
        assertThat(modifiedBike.isAvailable()).isFalse();
        assertThat(eventBus.getEmittedEvents()).isNotEmpty();
    }

    @Test
    void rejectsUnknownBikeId() {
        BikeRepository bikeRepository = new BikeRepositoryDouble();
        EventBusDouble eventBus = new EventBusDouble();
        BikeScanner bikeScanner = new BikeScanner(bikeRepository, eventBus);

        assertThatThrownBy(() -> bikeScanner.scan(new BikeId("wrongId")))
                .isInstanceOf(UnknownBikeException.class);
    }
}
