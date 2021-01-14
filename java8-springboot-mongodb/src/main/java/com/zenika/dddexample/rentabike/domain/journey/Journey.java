package com.zenika.dddexample.rentabike.domain.journey;

import com.zenika.dddexample.rentabike.domain.Entity;
import com.zenika.dddexample.rentabike.domain.bike.BikeId;
import com.zenika.dddexample.rentabike.domain.standard.Position;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Journey extends Entity<JourneyId> {

    private final BikeId bikeId;
    private final List<Position> positions;

    public Journey(JourneyId journeyId, BikeId bikeId, Position startPosition) {
        super(journeyId);
        requireNonNull(bikeId);
        requireNonNull(startPosition);
        this.bikeId = bikeId;
        this.positions = new ArrayList<>();
        positions.add(startPosition);
    }

    public void addWaypoint(Position waypoint) {
        requireNonNull(waypoint);
        positions.add(waypoint);
    }

    public double length() {
        double result = 0d;
        for (int i = 0; i < positions.size() - 1; i++) {
            Position position = positions.get(i);
            Position nextPosition = positions.get(i + 1);
            result += position.distanceTo(nextPosition);
        }
        return result;
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
