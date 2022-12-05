package fr.vernoux.rentabike.exposition;

import fr.vernoux.rentabike.application.JourneyCreator;
import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.EventBus;
import fr.vernoux.rentabike.domain.EventHandler;
import fr.vernoux.rentabike.domain.bike.BikeRentalStarted;
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
        var bikeRentalStarted = (BikeRentalStarted) event;
        var bikeId = bikeRentalStarted.bikeId();
        var startPosition = bikeRentalStarted.startPosition();
        journeyCreator.create(bikeId, startPosition);
    }
}
