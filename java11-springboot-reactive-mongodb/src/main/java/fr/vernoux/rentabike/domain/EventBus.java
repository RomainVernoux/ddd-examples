package fr.vernoux.rentabike.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EventBus {

    Flux<Event> on(Class<? extends Event> eventClass);

    Mono<Void> emitAll(List<Event> events);
}
