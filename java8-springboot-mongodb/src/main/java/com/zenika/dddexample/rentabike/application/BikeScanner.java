package com.zenika.dddexample.rentabike.application;

import com.zenika.dddexample.rentabike.domain.Event;
import com.zenika.dddexample.rentabike.domain.EventBus;
import com.zenika.dddexample.rentabike.domain.bike.Bike;
import com.zenika.dddexample.rentabike.domain.bike.BikeId;
import com.zenika.dddexample.rentabike.domain.bike.BikeRepository;
import com.zenika.dddexample.rentabike.domain.bike.UnknownBikeException;

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
