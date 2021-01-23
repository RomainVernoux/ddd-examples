package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGenerator implements IdGenerator {

    @Override
    public String uuid() {
        return UUID.randomUUID().toString();
    }
}
