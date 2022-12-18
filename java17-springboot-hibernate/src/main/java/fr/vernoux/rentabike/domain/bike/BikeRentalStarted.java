package fr.vernoux.rentabike.domain.bike;

import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.spatial.Position;

import static java.util.Objects.requireNonNull;

public record BikeRentalStarted(BikeId bikeId, Position startPosition) implements Event {

    public BikeRentalStarted {
        requireNonNull(bikeId);
        requireNonNull(startPosition);
    }
}
