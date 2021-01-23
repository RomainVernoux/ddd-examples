package fr.vernoux.rentabike.exposition;

import fr.vernoux.rentabike.domain.standard.Position;

public record JourneyDTO(String id, String bikeId, Position startPosition, Position endPosition) {
}
