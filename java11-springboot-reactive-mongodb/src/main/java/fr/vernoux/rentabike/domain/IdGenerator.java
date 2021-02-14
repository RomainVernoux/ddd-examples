package fr.vernoux.rentabike.domain;

import reactor.core.publisher.Mono;

public interface IdGenerator {

    Mono<String> uuid();
}
