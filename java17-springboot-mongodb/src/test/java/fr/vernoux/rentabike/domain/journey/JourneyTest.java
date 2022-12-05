package fr.vernoux.rentabike.domain.journey;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.spatial.Position;
import org.junit.jupiter.api.Test;

import static fr.vernoux.rentabike.utils.TestUtils.aRandomId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class JourneyTest {

    @Test
    void journeyLengthIsSumOfDistancesBetweenPositions() {
        var journeyId = new JourneyId(aRandomId());
        var bikeId = new BikeId(aRandomId());
        var startPosition = new Position(48.0, 2.0);
        var intermediatePosition = new Position(48.01d, 2.01d);
        var endPosition = new Position(48.02d, 2.02d);
        var journey = new Journey(journeyId, bikeId, startPosition);
        journey.addWaypoint(intermediatePosition);
        journey.addWaypoint(endPosition);

        var journeyLength = journey.length();

        assertThat(journeyLength).isCloseTo(2676d, offset(1d));
    }
}
