package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.IdGenerator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UUIDGenerator implements IdGenerator {

    @Override
    public Mono<String> uuid() {
        return Mono.fromCallable(() -> UUID.randomUUID().toString());
    }
}
