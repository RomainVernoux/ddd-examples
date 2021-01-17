package com.zenika.rentabike.domain.journey;

import com.zenika.rentabike.domain.bike.BikeId;

import java.util.List;

public interface JourneyRepository {

    void save(Journey journey);

    List<Journey> findAll();

    List<Journey> findAllByBike(BikeId bikeId);

    void deleteAll();
}
