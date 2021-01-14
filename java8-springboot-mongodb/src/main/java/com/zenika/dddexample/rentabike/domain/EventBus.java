package com.zenika.dddexample.rentabike.domain;

import java.util.List;

public interface EventBus {

    void subscribe(Class<? extends Event> eventClass, EventHandler eventHandler);

    void emitAll(List<Event> events);
}
