package com.zenika.rentabike.infrastructure;

import com.zenika.rentabike.domain.EventBus;
import com.zenika.rentabike.domain.EventHandler;
import com.zenika.rentabike.domain.bike.BikeId;
import com.zenika.rentabike.domain.bike.BikeMoved;
import com.zenika.rentabike.domain.bike.BikeRentalEnded;
import com.zenika.rentabike.domain.bike.BikeRentalStarted;
import com.zenika.rentabike.domain.standard.Position;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

class InMemoryEventBusTest {

    @Test
    void sendsEventsToSubscribers() {
        BikeId bikeId = new BikeId("1");
        Position position1 = new Position(48.0d, 2.0d);
        Position position2 = new Position(48.1d, 2.1d);
        EventBus eventBus = new InMemoryEventBus();
        EventHandler bikeRentalStartedHandler = mock(EventHandler.class);
        EventHandler bikeRentalEndedHandler = mock(EventHandler.class);
        eventBus.subscribe(BikeRentalStarted.class, bikeRentalStartedHandler);
        eventBus.subscribe(BikeRentalEnded.class, bikeRentalEndedHandler);
        BikeRentalStarted bikeRentalStarted = new BikeRentalStarted(bikeId, position1);
        BikeMoved bikeMoved = new BikeMoved(bikeId, position2);

        eventBus.emitAll(asList(bikeRentalStarted, bikeMoved));

        verify(bikeRentalStartedHandler).handle(bikeRentalStarted);
        verifyNoMoreInteractions(bikeRentalStartedHandler);
        verifyNoMoreInteractions(bikeRentalEndedHandler);
    }
}
