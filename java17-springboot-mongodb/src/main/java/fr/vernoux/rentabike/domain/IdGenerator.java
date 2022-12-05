package fr.vernoux.rentabike.domain;

import java.util.UUID;

public interface IdGenerator {

    UUID next();
}
