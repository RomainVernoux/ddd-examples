package fr.vernoux.rentabike.domain.spatial;

import static java.util.Objects.requireNonNull;

public record Position(double lat, double lng) {

    private static final double EARTH_RADIUS = 6371000d;

    public double distanceTo(Position other) {
        requireNonNull(other);
        var averageLatitude = (toAngle(lat) + other.toAngle(lat)) / 2;
        var x = (toAngle(lng) - toAngle(other.lng)) * Math.cos(averageLatitude);
        var y = toAngle(lat) - toAngle(other.lat);
        return EARTH_RADIUS * Math.sqrt(x * x + y * y);
    }

    private double toAngle(double longAngle) {
        return longAngle * 2 * Math.PI / 360;
    }
}
