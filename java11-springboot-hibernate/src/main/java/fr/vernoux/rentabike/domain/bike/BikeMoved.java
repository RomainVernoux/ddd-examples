package fr.vernoux.rentabike.domain.bike;

import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.standard.Position;

import static java.util.Objects.requireNonNull;

public class BikeMoved extends Event {

    private final BikeId bikeId;
    private final Position newPosition;

    public BikeMoved(BikeId bikeId, Position newPosition) {
        requireNonNull(bikeId);
        requireNonNull(newPosition);
        this.bikeId = bikeId;
        this.newPosition = newPosition;
    }

    public BikeId getBikeId() {
        return bikeId;
    }

    public Position getNewPosition() {
        return newPosition;
    }
}
