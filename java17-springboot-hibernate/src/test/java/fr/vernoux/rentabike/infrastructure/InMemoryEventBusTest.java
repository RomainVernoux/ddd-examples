package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.EventHandler;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeMoved;
import fr.vernoux.rentabike.domain.bike.BikeRentalEnded;
import fr.vernoux.rentabike.domain.bike.BikeRentalStarted;
import org.junit.jupiter.api.Test;

import static fr.vernoux.rentabike.utils.TestUtils.aRandomId;
import static fr.vernoux.rentabike.utils.TestUtils.aRandomPosition;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

class InMemoryEventBusTest {

    @Test
    void sendsEventsToSubscribers() {
        var bikeId = new BikeId(aRandomId());
        var position1 = aRandomPosition();
        var position2 = aRandomPosition();
        var eventBus = new InMemoryEventBus();
        var bikeRentalStartedHandler = mock(EventHandler.class);
        var bikeRentalEndedHandler = mock(EventHandler.class);
        eventBus.subscribe(BikeRentalStarted.class, bikeRentalStartedHandler);
        eventBus.subscribe(BikeRentalEnded.class, bikeRentalEndedHandler);
        var bikeRentalStarted = new BikeRentalStarted(bikeId, position1);
        var bikeMoved = new BikeMoved(bikeId, position2);

        eventBus.emitAll(asList(bikeRentalStarted, bikeMoved));

        verify(bikeRentalStartedHandler).handle(bikeRentalStarted);
        verifyNoMoreInteractions(bikeRentalStartedHandler);
        verifyNoMoreInteractions(bikeRentalEndedHandler);
    }
}
