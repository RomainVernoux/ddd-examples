package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InMemoryIdGenerator implements IdGenerator {

    @Override
    public UUID next() {
        return UUID.randomUUID();
    }
}
