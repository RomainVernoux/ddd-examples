package fr.vernoux.rentabike.domain.bike;

import static java.util.Objects.requireNonNull;

public record BikeId(String value) {

    public BikeId {
        requireNonNull(value);
    }
}
