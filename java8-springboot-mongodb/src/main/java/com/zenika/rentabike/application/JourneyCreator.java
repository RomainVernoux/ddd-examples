package com.zenika.rentabike.application;

import com.zenika.rentabike.domain.IdGenerator;
import com.zenika.rentabike.domain.bike.BikeId;
import com.zenika.rentabike.domain.journey.Journey;
import com.zenika.rentabike.domain.journey.JourneyId;
import com.zenika.rentabike.domain.journey.JourneyRepository;
import com.zenika.rentabike.domain.standard.Position;

import static java.util.Objects.requireNonNull;

public class JourneyCreator {

    private final JourneyRepository journeyRepository;
    private final IdGenerator idGenerator;

    public JourneyCreator(JourneyRepository journeyRepository, IdGenerator idGenerator) {
        this.journeyRepository = journeyRepository;
        this.idGenerator = idGenerator;
    }

    public void create(BikeId bikeId, Position startPosition) {
        requireNonNull(bikeId);
        requireNonNull(startPosition);
        JourneyId journeyId = new JourneyId(idGenerator.uuid());
        Journey journey = new Journey(journeyId, bikeId, startPosition);
        journeyRepository.save(journey);
    }
}
