package fr.vernoux.rentabike.domain.bike;

import org.junit.jupiter.api.Test;

import static fr.vernoux.rentabike.utils.TestUtils.aRandomId;
import static fr.vernoux.rentabike.utils.TestUtils.aRandomPosition;
import static org.assertj.core.api.Assertions.assertThat;

class BikeTest {

    @Test
    void aBikeIsAvailableByDefault() {
        var bikeId = new BikeId(aRandomId());
        var bikePosition = aRandomPosition();
        var bike = Bike.enroll(bikeId, bikePosition);

        assertThat(bike.isAvailable()).isTrue();
    }

    @Test
    void scanningAnAvailableBikeStartsRental() {
        var bike = anAvailableBike();

        var events = bike.scan();

        assertThat(events).contains(new BikeRentalStarted(bike.getId(), bike.getPosition()));
    }

    @Test
    void scanningAnAvailableBikeMakesItUnavailable() {
        var bike = anAvailableBike();

        bike.scan();

        assertThat(bike.isAvailable()).isFalse();
    }

    @Test
    void scanningAnUnavailableBikeEndsRental() {
        var bike = anUnavailableBike();

        var events = bike.scan();

        assertThat(events).contains(new BikeRentalEnded(bike.getId(), bike.getPosition()));
    }

    @Test
    void scanningAnUnavailableBikeMakesItAvailable() {
        var bike = anUnavailableBike();

        bike.scan();

        assertThat(bike.isAvailable()).isTrue();
    }

    @Test
    void movingABikeChangesItsPosition() {
        var bike = anUnavailableBike();
        var newPosition = aRandomPosition();

        var events = bike.move(newPosition);

        assertThat(events).contains(new BikeMoved(bike.getId(), newPosition));
        assertThat(bike.getPosition()).isEqualTo(newPosition);
    }

    @Test
    void lastBikePositionIsUsedAtRentalEnd() {
        var bike = anUnavailableBike();
        var intermediatePosition = aRandomPosition();
        var lastPosition = aRandomPosition();

        bike.move(intermediatePosition);
        bike.move(lastPosition);
        var events = bike.scan();

        assertThat(events).contains(new BikeRentalEnded(bike.getId(), lastPosition));
    }

    private Bike anAvailableBike() {
        var bikeId = new BikeId(aRandomId());
        var bikePosition = aRandomPosition();
        return Bike.enroll(bikeId, bikePosition);
    }

    private Bike anUnavailableBike() {
        var bike = anAvailableBike();
        bike.scan();
        return bike;
    }
}
