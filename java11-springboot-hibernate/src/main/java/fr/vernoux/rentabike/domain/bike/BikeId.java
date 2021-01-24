package fr.vernoux.rentabike.domain.bike;

import fr.vernoux.rentabike.domain.ValueObjectId;

public final class BikeId extends ValueObjectId {

    public BikeId(String value) {
        super(value);
    }

    protected BikeId() {
        // For hibernate
    }
}
