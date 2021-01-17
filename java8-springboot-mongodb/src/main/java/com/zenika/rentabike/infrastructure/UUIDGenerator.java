package com.zenika.rentabike.infrastructure;

import com.zenika.rentabike.domain.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGenerator implements IdGenerator {

    @Override
    public String uuid() {
        return UUID.randomUUID().toString();
    }
}
