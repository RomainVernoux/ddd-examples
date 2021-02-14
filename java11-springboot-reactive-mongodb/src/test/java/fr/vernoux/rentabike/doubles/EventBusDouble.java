package fr.vernoux.rentabike.doubles;

import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class EventBusDouble implements EventBus {

    private final List<Event> emittedEvents = new ArrayList<>();

    @Override
    public Flux<Event> on(Class<? extends Event> eventClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<Void> emitAll(List<Event> events) {
        return Mono.create(s -> {
            emittedEvents.addAll(events);
            s.success();
        });
    }

    public List<Event> getEmittedEvents() {
        return emittedEvents;
    }
}
