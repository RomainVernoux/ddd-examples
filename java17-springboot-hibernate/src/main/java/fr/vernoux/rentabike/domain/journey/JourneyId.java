package fr.vernoux.rentabike.domain.journey;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record JourneyId(UUID value) {

    public JourneyId {
        requireNonNull(value);
    }
}
