package com.zenika.rentabike.application;

import com.zenika.rentabike.domain.bike.BikeId;
import com.zenika.rentabike.domain.journey.Journey;
import com.zenika.rentabike.domain.journey.JourneyRepository;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class JourneySearcher {

    private final JourneyRepository journeyRepository;

    public JourneySearcher(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    public List<Journey> searchJourneyInProgressForBike(BikeId bikeId) {
        requireNonNull(bikeId);
        return journeyRepository.findAllByBike(bikeId);
    }
}
