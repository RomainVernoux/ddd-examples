package com.zenika.rentabike.domain.bike;

import com.zenika.rentabike.domain.Entity;
import com.zenika.rentabike.domain.Event;
import com.zenika.rentabike.domain.standard.Position;

import java.util.List;

import static com.zenika.rentabike.domain.bike.BikeStatus.LOCKED;
import static com.zenika.rentabike.domain.bike.BikeStatus.RENTAL_IN_PROGRESS;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

public class Bike extends Entity<BikeId> {

    private Position position;
    private BikeStatus status;

    public Bike(BikeId id, Position position) {
        super(id);
        requireNonNull(position);
        this.position = position;
        this.status = LOCKED;
    }

    public boolean isAvailable() {
        return status == LOCKED;
    }

    public List<Event> scan() {
        if (isAvailable()) {
            return startRental();
        } else {
            return endRental();
        }
    }

    public List<Event> move(Position newPosition) {
        requireNonNull(newPosition);
        position = newPosition;
        return singletonList(new BikeMoved(id, newPosition));
    }

    private List<Event> startRental() {
        status = RENTAL_IN_PROGRESS;
        return singletonList(new BikeRentalStarted(id, position));
    }

    private List<Event> endRental() {
        status = LOCKED;
        return singletonList(new BikeRentalEnded(id, position));
    }

    public BikeStatus getStatus() {
        return status;
    }

    public Position getPosition() {
        return position;
    }
}
