package fr.vernoux.rentabike.domain.journey;

import fr.vernoux.rentabike.domain.ValueObjectId;

public class JourneyId extends ValueObjectId {

    public JourneyId(String value) {
        super(value);
    }

    protected JourneyId() {
        // For hibernate
    }
}
