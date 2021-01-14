package com.zenika.dddexample.rentabike.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Entity<Id> {

    protected final Id id;

    public Entity(Id id) {
        requireNonNull(id);
        this.id = id;
    }

    public Id getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
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
