package com.zenika.rentabike.domain.journey;

import com.zenika.rentabike.domain.bike.BikeId;
import com.zenika.rentabike.domain.standard.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class JourneyTest {

    @Test
    void journeyLengthIsSumOfDistancesBetweenPositions() {
        JourneyId journeyId = new JourneyId("1");
        BikeId bikeId = new BikeId("2");
        Position startPosition = new Position(48.0, 2.0);
        Position intermediatePosition = new Position(48.01d, 2.01d);
        Position endPosition = new Position(48.02d, 2.02d);
        Journey journey = new Journey(journeyId, bikeId, startPosition);
        journey.addWaypoint(intermediatePosition);
        journey.addWaypoint(endPosition);

        double journeyLength = journey.length();

        assertThat(journeyLength).isCloseTo(2676d, offset(1d));
    }
}
