package fr.vernoux.rentabike.domain.journey;

import static java.util.Objects.requireNonNull;

public record JourneyId(String value) {

    public JourneyId {
        requireNonNull(value);
    }
}
