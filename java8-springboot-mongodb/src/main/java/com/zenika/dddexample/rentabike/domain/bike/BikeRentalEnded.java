package com.zenika.dddexample.rentabike.domain.bike;

import com.zenika.dddexample.rentabike.domain.Event;
import com.zenika.dddexample.rentabike.domain.standard.Position;

import static java.util.Objects.requireNonNull;

public class BikeRentalEnded extends Event {

    private final BikeId bikeId;
    private final Position endPosition;

    public BikeRentalEnded(BikeId bikeId, Position endPosition) {
        requireNonNull(bikeId);
        requireNonNull(endPosition);
        this.bikeId = bikeId;
        this.endPosition = endPosition;
    }

    public BikeId getBikeId() {
        return bikeId;
    }

    public Position getEndPosition() {
        return endPosition;
    }
}
