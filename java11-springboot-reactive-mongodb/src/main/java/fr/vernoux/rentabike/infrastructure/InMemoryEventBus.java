package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.EventBus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

@Component
public class InMemoryEventBus implements EventBus {

    private final Map<Class<? extends Event>, Sinks.Many<Event>> streams = new HashMap<>();

    @Override
    public Flux<Event> on(Class<? extends Event> eventClass) {
        return streamForClass(eventClass).asFlux();
    }

    @Override
    public Mono<Void> emitAll(List<Event> events) {
        return Mono.create(s -> {
            events.forEach(event -> streamForClass(event.getClass()).emitNext(event, FAIL_FAST));
            s.success();
        });
    }

    private Sinks.Many<Event> streamForClass(Class<? extends Event> eventClass) {
        return streams.computeIfAbsent(eventClass, key -> Sinks.many().multicast().onBackpressureBuffer());
    }
}