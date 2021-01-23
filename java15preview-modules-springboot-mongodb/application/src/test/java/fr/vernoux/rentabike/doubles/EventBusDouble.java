package fr.vernoux.rentabike.doubles;

import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.EventBus;
import fr.vernoux.rentabike.domain.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class EventBusDouble implements EventBus {

    private final List<Event> emittedEvents = new ArrayList<>();

    @Override
    public void subscribe(Class<? extends Event> eventClass, EventHandler eventHandler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void emitAll(List<Event> events) {
        emittedEvents.addAll(events);
    }

    public List<Event> getEmittedEvents() {
        return emittedEvents;
    }
}
