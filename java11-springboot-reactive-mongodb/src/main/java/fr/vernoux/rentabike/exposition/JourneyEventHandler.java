package fr.vernoux.rentabike.exposition;

import fr.vernoux.rentabike.application.JourneyCreator;
import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.EventBus;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRentalStarted;
import fr.vernoux.rentabike.domain.standard.Position;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JourneyEventHandler {

    private final JourneyCreator journeyCreator;

    public JourneyEventHandler(EventBus eventBus, JourneyCreator journeyCreator) {
        this.journeyCreator = journeyCreator;
        eventBus.on(BikeRentalStarted.class).flatMap(this::handle).subscribe();
    }

    public Mono<Void> handle(Event event) {
        BikeRentalStarted bikeRentalStarted = (BikeRentalStarted) event;
        BikeId bikeId = bikeRentalStarted.getBikeId();
        Position startPosition = bikeRentalStarted.getStartPosition();
        return journeyCreator.create(bikeId, startPosition);
    }
}
