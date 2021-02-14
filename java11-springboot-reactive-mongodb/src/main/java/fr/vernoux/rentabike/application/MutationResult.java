package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.Entity;
import fr.vernoux.rentabike.domain.Event;

import java.util.List;

public class MutationResult<T extends Entity<?>> {

    private final T mutatedEntity;
    private final List<Event> emittedEvents;

    private MutationResult(T mutatedEntity, List<Event> emittedEvents) {
        this.mutatedEntity = mutatedEntity;
        this.emittedEvents = emittedEvents;
    }

    public T entity() {
        return mutatedEntity;
    }

    public List<Event> events() {
        return emittedEvents;
    }

    public static <T extends Entity<?>> MutationResult<T> of(T mutatedEntity, List<Event> emittedEvents) {
        return new MutationResult<>(mutatedEntity, emittedEvents);
    }
}
