package fr.vernoux.rentabike.domain.bike;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record BikeId(UUID value) {

    public BikeId {
        requireNonNull(value);
    }
}
