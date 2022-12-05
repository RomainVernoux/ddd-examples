package fr.vernoux.rentabike.utils;

import fr.vernoux.rentabike.domain.spatial.Position;

import java.util.UUID;

public class TestUtils {

    public static UUID aRandomId() {
        return UUID.randomUUID();
    }

    public static Position aRandomPosition() {
        return new Position(Math.random() * 180 - 90, Math.random() * 360 - 180);
    }
}
