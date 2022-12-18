package fr.vernoux.rentabike.domain.bike;

import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.spatial.Position;

import static java.util.Objects.requireNonNull;

public record BikeMoved(BikeId bikeId, Position newPosition) implements Event {

    public BikeMoved {
        requireNonNull(bikeId);
        requireNonNull(newPosition);
    }
}
