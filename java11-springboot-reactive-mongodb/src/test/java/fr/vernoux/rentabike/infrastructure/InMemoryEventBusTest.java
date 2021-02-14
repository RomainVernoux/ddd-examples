package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.EventBus;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeMoved;
import fr.vernoux.rentabike.domain.bike.BikeRentalStarted;
import fr.vernoux.rentabike.domain.standard.Position;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

class InMemoryEventBusTest {

    @Test
    void sendsEventsToSubscribers() {
        BikeId bikeId = new BikeId("1");
        Position position1 = new Position(48.0d, 2.0d);
        Position position2 = new Position(48.1d, 2.1d);
        EventBus eventBus = new InMemoryEventBus();
        BikeRentalStarted bikeRentalStarted = new BikeRentalStarted(bikeId, position1);
        BikeMoved bikeMoved = new BikeMoved(bikeId, position2);
        Flux<Event> bikeRentalStartedEvents = eventBus.on(BikeRentalStarted.class);
        Flux<Event> bikeMovedEvents = eventBus.on(BikeMoved.class);

        eventBus.emitAll(List.of(bikeRentalStarted, bikeMoved)).block();

        StepVerifier.create(bikeRentalStartedEvents)
                .expectNext(bikeRentalStarted)
                .expectTimeout(Duration.ofMillis(100))
                .verify();

        StepVerifier.create(bikeMovedEvents)
                .expectNext(bikeMoved)
                .expectTimeout(Duration.ofMillis(100))
                .verify();
    }
}
