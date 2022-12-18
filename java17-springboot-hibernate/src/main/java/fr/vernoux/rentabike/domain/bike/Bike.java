package fr.vernoux.rentabike.domain.bike;

import fr.vernoux.rentabike.domain.Event;
import fr.vernoux.rentabike.domain.spatial.Position;

import java.util.List;

import static fr.vernoux.rentabike.domain.bike.BikeStatus.LOCKED;
import static fr.vernoux.rentabike.domain.bike.BikeStatus.RENTAL_IN_PROGRESS;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

public class Bike {

    private final BikeId id;
    private Position position;
    private BikeStatus status;

    public Bike(BikeId id, Position position, BikeStatus status) {
        this.id = requireNonNull(id);
        this.position = requireNonNull(position);
        this.status = requireNonNull(status);
    }

    public static Bike enroll(BikeId id, Position position) {
        return new Bike(id, position, LOCKED);
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
        position = requireNonNull(newPosition);
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

    public BikeId getId() {
        return id;
    }

    public BikeStatus getStatus() {
        return status;
    }

    public Position getPosition() {
        return position;
    }
}
