package com.zenika.dddexample.rentabike.domain.bike;

import com.zenika.dddexample.rentabike.domain.standard.Zone;

import java.util.List;

public interface BikeRepository {

    void save(Bike bike);

    Bike getById(BikeId id) throws UnknownBikeException;

    List<Bike> findAllByStatusInZone(BikeStatus status, Zone zone);

    void deleteAll();
}
