package fr.vernoux.rentabike.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import static java.util.Objects.requireNonNull;

@Embeddable
@MappedSuperclass
public abstract class ValueObjectId extends ValueObject implements Serializable {

    @Column(name = "ID")
    private String value;

    public ValueObjectId(String value) {
        requireNonNull(value);
        this.value = value;
    }

    protected ValueObjectId() {
        // For hibernate
    }

    public String getValue() {
        return value;
    }
}
