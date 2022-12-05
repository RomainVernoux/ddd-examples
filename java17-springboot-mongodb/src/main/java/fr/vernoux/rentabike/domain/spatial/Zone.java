package fr.vernoux.rentabike.domain.spatial;

public record Zone(double lat, double lng, long radius) {

    public Zone {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius should not be negative");
        }
    }
}
