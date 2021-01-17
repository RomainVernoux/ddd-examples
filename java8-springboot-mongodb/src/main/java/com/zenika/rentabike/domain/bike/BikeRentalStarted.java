package com.zenika.rentabike.domain.bike;

import com.zenika.rentabike.domain.Event;
import com.zenika.rentabike.domain.standard.Position;

import static java.util.Objects.requireNonNull;

public class BikeRentalStarted extends Event {

    private final BikeId bikeId;
    private final Position startPosition;

    public BikeRentalStarted(BikeId bikeId, Position startPosition) {
        requireNonNull(bikeId);
        requireNonNull(startPosition);
        this.bikeId = bikeId;
        this.startPosition = startPosition;
    }

    public BikeId getBikeId() {
        return bikeId;
    }

    public Position getStartPosition() {
        return startPosition;
    }
}
