package com.zenika.dddexample.rentabike.application;

import com.zenika.dddexample.rentabike.domain.bike.BikeId;
import com.zenika.dddexample.rentabike.domain.journey.Journey;
import com.zenika.dddexample.rentabike.domain.journey.JourneyRepository;

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
