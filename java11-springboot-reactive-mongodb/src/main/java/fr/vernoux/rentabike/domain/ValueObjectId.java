package fr.vernoux.rentabike.domain;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public abstract class ValueObjectId extends ValueObject implements Serializable {

    private final String value;

    public ValueObjectId(String value) {
        requireNonNull(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
