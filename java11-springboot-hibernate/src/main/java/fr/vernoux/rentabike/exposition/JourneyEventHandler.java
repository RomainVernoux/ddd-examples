package fr.vernoux.rentabike.exposition;

import fr.vernoux.rentabike.application.JourneyCreator;
import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.EventBus;
import fr.vernoux.rentabike.domain.EventHandler;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRentalStarted;
import fr.vernoux.rentabike.domain.standard.Position;
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
