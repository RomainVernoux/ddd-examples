package fr.vernoux.rentabike.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@MappedSuperclass
public class DomainEntity<Id extends ValueObjectId> {

    @EmbeddedId
    @Type(type = "ValueObjectId")
    protected Id id;

    public DomainEntity(Id id) {
        requireNonNull(id);
        this.id = id;
    }

    protected DomainEntity() {
        // For hibernate
    }

    public Id getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainEntity<?> domainEntity = (DomainEntity<?>) o;
        return Objects.equals(id, domainEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
