package fr.vernoux.rentabike.exposition;

import fr.vernoux.rentabike.application.JourneyStarter;
import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.EventBus;
import fr.vernoux.rentabike.domain.EventHandler;
import fr.vernoux.rentabike.domain.bike.BikeRentalStarted;
import org.springframework.stereotype.Component;

@Component
public class JourneyEventHandler implements EventHandler {

    private final JourneyStarter journeyStarter;

    public JourneyEventHandler(EventBus eventBus, JourneyStarter journeyStarter) {
        this.journeyStarter = journeyStarter;
        eventBus.subscribe(BikeRentalStarted.class, this);
    }

    @Override
    public void handle(Event event) {
        var bikeRentalStarted = (BikeRentalStarted) event;
        var bikeId = bikeRentalStarted.bikeId();
        var startPosition = bikeRentalStarted.startPosition();
        journeyStarter.create(bikeId, startPosition);
    }
}
