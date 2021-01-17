package com.zenika.rentabike.doubles;

import com.zenika.rentabike.domain.bike.BikeId;
import com.zenika.rentabike.domain.journey.Journey;
import com.zenika.rentabike.domain.journey.JourneyId;
import com.zenika.rentabike.domain.journey.JourneyRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class JourneyRepositoryDouble implements JourneyRepository {

    private final Map<JourneyId, Journey> database = new HashMap<>();

    @Override
    public void save(Journey bike) {
        database.put(bike.getId(), bike);
    }

    @Override
    public List<Journey> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public List<Journey> findAllByBike(BikeId bikeId) {
        return database.values().stream()
                .filter(journey -> journey.getBikeId().equals(bikeId))
                .collect(toList());
    }

    @Override
    public void deleteAll() {
        database.clear();
    }
}
