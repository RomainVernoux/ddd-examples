package com.zenika.dddexample.rentabike.domain.standard;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class PositionTest {

    @Test
    void computesDistanceToAnotherPoint() {
        Position position1 = new Position(48d, 2d);
        Position position2 = new Position(48.01d, 2.01d);

        double distance = position1.distanceTo(position2);

        assertThat(distance).isCloseTo(1338d, offset(1d));
    }

    @Test
    void distanceWithSamePointIsZero() {
        Position position = new Position(48d, 2d);

        double distance = position.distanceTo(position);

        assertThat(distance).isEqualTo(0);
    }
}
