package fr.vernoux.rentabike.exposition;

import fr.vernoux.rentabike.domain.standard.Position;

public class BikeDTO {

    private final String id;
    private final Position position;

    public BikeDTO(String id, Position position) {
        this.id = id;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }
}
