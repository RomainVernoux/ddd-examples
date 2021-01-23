package fr.vernoux.rentabike.domain.bike;

import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.standard.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BikeTest {

    @Test
    void aBikeIsAvailableByDefault() {
        BikeId bikeId = new BikeId("123");
        Position bikePosition = new Position(48d, 2d);
        Bike bike = new Bike(bikeId, bikePosition);

        assertThat(bike.isAvailable()).isTrue();
    }

    @Test
    void scanningAnAvailableBikeStartsRental() {
        Bike bike = anAvailableBike();

        List<Event> events = bike.scan();

        assertThat(events).contains(new BikeRentalStarted(bike.getId(), bike.getPosition()));
    }

    @Test
    void scanningAnAvailableBikeMakesItUnavailable() {
        Bike bike = anAvailableBike();

        bike.scan();

        assertThat(bike.isAvailable()).isFalse();
    }

    @Test
    void scanningAnUnavailableBikeEndsRental() {
        Bike bike = anUnavailableBike();

        List<Event> events = bike.scan();

        assertThat(events).contains(new BikeRentalEnded(bike.getId(), bike.getPosition()));
    }

    @Test
    void scanningAnUnavailableBikeMakesItAvailable() {
        Bike bike = anUnavailableBike();

        bike.scan();

        assertThat(bike.isAvailable()).isTrue();
    }

    @Test
    void aBikeCanMove() {
        Bike bike = anUnavailableBike();
        Position newPosition = new Position(48.1d, 2.1d);

        List<Event> events = bike.move(newPosition);

        assertThat(events).contains(new BikeMoved(bike.getId(), newPosition));
        assertThat(bike.getPosition()).isEqualTo(newPosition);
    }

    @Test
    void lastBikePositionIsUsedAtRentalEnd() {
        Bike bike = anUnavailableBike();
        Position intermediatePosition = new Position(48.1d, 2.1d);
        Position lastPosition = new Position(48.2d, 2.2d);

        bike.move(intermediatePosition);
        bike.move(lastPosition);
        List<Event> events = bike.scan();

        assertThat(events).contains(new BikeRentalEnded(bike.getId(), lastPosition));
    }

    private Bike anAvailableBike() {
        BikeId bikeId = new BikeId("123");
        Position bikePosition = new Position(48d, 2d);
        return new Bike(bikeId, bikePosition);
    }

    private Bike anUnavailableBike() {
        Bike bike = anAvailableBike();
        bike.scan();
        return bike;
    }
}
