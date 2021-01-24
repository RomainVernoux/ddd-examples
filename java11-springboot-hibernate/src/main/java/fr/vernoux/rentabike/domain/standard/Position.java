package fr.vernoux.rentabike.domain.standard;

import fr.vernoux.rentabike.domain.ValueObject;

import javax.persistence.Embeddable;

import static java.util.Objects.requireNonNull;

@Embeddable
public class Position extends ValueObject {

    private static final double EARTH_RADIUS = 6371000d;
    private double lat;
    private double lng;

    public Position(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    protected Position() {
        // For hibernate
    }

    public double distanceTo(Position other) {
        requireNonNull(other);
        double averageLatitude = (toAngle(lat) + other.toAngle(lat)) / 2;
        double x = (toAngle(lng) - toAngle(other.lng)) * Math.cos(averageLatitude);
        double y = toAngle(lat) - toAngle(other.lat);
        return EARTH_RADIUS * Math.sqrt(x * x + y * y);
    }

    private double toAngle(double longAngle) {
        return longAngle * 2 * Math.PI / 360;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
