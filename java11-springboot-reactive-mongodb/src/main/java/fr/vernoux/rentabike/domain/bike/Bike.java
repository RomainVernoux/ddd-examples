package fr.vernoux.rentabike.domain.bike;

import fr.vernoux.rentabike.domain.Entity;
import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.standard.Position;

import java.util.List;

import static fr.vernoux.rentabike.domain.bike.BikeStatus.LOCKED;
import static fr.vernoux.rentabike.domain.bike.BikeStatus.RENTAL_IN_PROGRESS;
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

    private Bike() {
        // For persistence
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
        return List.of(new BikeMoved(id, newPosition));
    }

    private List<Event> startRental() {
        status = RENTAL_IN_PROGRESS;
        return List.of(new BikeRentalStarted(id, position));
    }

    private List<Event> endRental() {
        status = LOCKED;
        return List.of(new BikeRentalEnded(id, position));
    }

    public BikeStatus getStatus() {
        return status;
    }

    public Position getPosition() {
        return position;
    }
}
