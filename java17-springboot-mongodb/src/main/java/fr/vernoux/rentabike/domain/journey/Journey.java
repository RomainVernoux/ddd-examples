package fr.vernoux.rentabike.domain.journey;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.spatial.Position;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Journey {

    private JourneyId id;
    private BikeId bikeId;
    private List<Position> positions;

    private Journey() {
    }

    public static Journey start(JourneyId id, BikeId bikeId, Position startPosition) {
        Journey journey = new Journey();
        journey.id = requireNonNull(id);
        journey.bikeId = requireNonNull(bikeId);
        journey.positions = new ArrayList<>();
        journey.positions.add(requireNonNull(startPosition));
        return journey;
    }

    public void addWaypoint(Position waypoint) {
        requireNonNull(waypoint);
        positions.add(waypoint);
    }

    public double length() {
        var result = 0d;
        for (int i = 0; i < positions.size() - 1; i++) {
            var position = positions.get(i);
            var nextPosition = positions.get(i + 1);
            result += position.distanceTo(nextPosition);
        }
        return result;
    }

    public JourneyId getId() {
        return id;
    }

    public BikeId getBikeId() {
        return bikeId;
    }

    public Position getStartPosition() {
        return positions.get(0);
    }

    public Position getEndPosition() {
        return positions.get(positions.size() - 1);
    }
}
