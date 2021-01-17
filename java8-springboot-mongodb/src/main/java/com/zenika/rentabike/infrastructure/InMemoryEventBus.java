package com.zenika.rentabike.infrastructure;

import com.zenika.rentabike.domain.Event;
import com.zenika.rentabike.domain.EventBus;
import com.zenika.rentabike.domain.EventHandler;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.Collections.emptySet;

@Component
public class InMemoryEventBus implements EventBus {

    private final Map<Class<? extends Event>, Set<EventHandler>> subscribers = new HashMap<>();

    @Override
    public void subscribe(Class<? extends Event> eventClass, EventHandler eventHandler) {
        subscribers.computeIfAbsent(eventClass, key -> new HashSet<>()).add(eventHandler);
    }

    @Override
    public void emitAll(List<Event> events) {
        events.forEach(event ->
                subscribers.getOrDefault(event.getClass(), emptySet()).forEach(subscriber ->
                        subscriber.handle(event)
                )
        );
    }
}
