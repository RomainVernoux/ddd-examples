package fr.vernoux.rentabike.domain;

import static java.util.Objects.requireNonNull;

public abstract class ValueObjectId extends ValueObject {

    private final String value;

    public ValueObjectId(String value) {
        requireNonNull(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
