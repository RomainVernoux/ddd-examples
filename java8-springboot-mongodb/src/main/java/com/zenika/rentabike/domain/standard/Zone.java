package com.zenika.rentabike.domain.standard;

import com.zenika.rentabike.domain.ValueObject;

public class Zone extends ValueObject {

    private final double lat;
    private final double lng;
    private final long radius;

    public Zone(double lat, double lng, long radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius should not be negative");
        }
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public long getRadius() {
        return radius;
    }
}
