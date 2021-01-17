package com.zenika.rentabike.application;

import com.zenika.rentabike.domain.bike.Bike;
import com.zenika.rentabike.domain.bike.BikeRepository;
import com.zenika.rentabike.domain.standard.Zone;

import java.util.List;

import static com.zenika.rentabike.domain.bike.BikeStatus.LOCKED;
import static java.util.Objects.requireNonNull;

public class BikeSearcher {

    private final BikeRepository bikeRepository;

    public BikeSearcher(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public List<Bike> getAllAvailableInZone(Zone zone) {
        requireNonNull(zone);
        return bikeRepository.findAllByStatusInZone(LOCKED, zone);
    }
}
