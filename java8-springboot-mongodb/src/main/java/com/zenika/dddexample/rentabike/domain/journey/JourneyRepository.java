package com.zenika.dddexample.rentabike.domain.journey;

import com.zenika.dddexample.rentabike.domain.bike.BikeId;

import java.util.List;

public interface JourneyRepository {

    void save(Journey journey);

    List<Journey> findAll();

    List<Journey> findAllByBike(BikeId bikeId);

    void deleteAll();
}
