package com.zenika.rentabike.application;

import com.zenika.rentabike.domain.Event;
import com.zenika.rentabike.domain.EventBus;
import com.zenika.rentabike.domain.bike.Bike;
import com.zenika.rentabike.domain.bike.BikeId;
import com.zenika.rentabike.domain.bike.BikeRepository;
import com.zenika.rentabike.domain.bike.UnknownBikeException;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class BikeScanner {

    private final BikeRepository bikeRepository;
    private final EventBus eventBus;

    public BikeScanner(BikeRepository bikeRepository, EventBus eventBus) {
        this.bikeRepository = bikeRepository;
        this.eventBus = eventBus;
    }

    public void scan(BikeId bikeId) throws UnknownBikeException {
        requireNonNull(bikeId);
        Bike bike = bikeRepository.getById(bikeId);
        List<Event> events = bike.scan();
        bikeRepository.save(bike);
        eventBus.emitAll(events);
    }
}
