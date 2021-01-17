package fr.vernoux.rentabike.domain.bike;

import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.standard.Position;

import static java.util.Objects.requireNonNull;

public record BikeRentalEnded(BikeId bikeId, Position endPosition) implements Event {

    public BikeRentalEnded {
        requireNonNull(bikeId);
        requireNonNull(endPosition);
    }
}
