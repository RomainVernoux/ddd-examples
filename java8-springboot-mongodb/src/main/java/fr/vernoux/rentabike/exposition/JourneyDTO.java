package fr.vernoux.rentabike.exposition;

import fr.vernoux.rentabike.domain.standard.Position;

public class JourneyDTO {

    private final String id;
    private final String bikeId;
    private final Position startPosition;
    private final Position endPosition;

    public JourneyDTO(String id, String bikeId, Position startPosition, Position endPosition) {
        this.id = id;
        this.bikeId = bikeId;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public String getId() {
        return id;
    }

    public String getBikeId() {
        return bikeId;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }
}
