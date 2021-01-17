package com.zenika.rentabike.doubles;

import com.zenika.rentabike.domain.Event;
import com.zenika.rentabike.domain.EventBus;
import com.zenika.rentabike.domain.EventHandler;

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
