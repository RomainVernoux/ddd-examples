package com.zenika.rentabike.exposition;

import com.zenika.rentabike.application.JourneyCreator;
import com.zenika.rentabike.domain.Event;
import com.zenika.rentabike.domain.EventBus;
import com.zenika.rentabike.domain.EventHandler;
import com.zenika.rentabike.domain.bike.BikeId;
import com.zenika.rentabike.domain.bike.BikeRentalStarted;
import com.zenika.rentabike.domain.standard.Position;
import org.springframework.stereotype.Component;

@Component
public class JourneyEventHandler implements EventHandler {

    private final JourneyCreator journeyCreator;

    public JourneyEventHandler(EventBus eventBus, JourneyCreator journeyCreator) {
        this.journeyCreator = journeyCreator;
        eventBus.subscribe(BikeRentalStarted.class, this);
    }

    @Override
    public void handle(Event event) {
        BikeRentalStarted bikeRentalStarted = (BikeRentalStarted) event;
        BikeId bikeId = bikeRentalStarted.getBikeId();
        Position startPosition = bikeRentalStarted.getStartPosition();
        journeyCreator.create(bikeId, startPosition);
    }
}
