package br.com.beergo.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class BarMapsRequest {
    private MapsLocation location;
    private long radius;

    public BarMapsRequest() {
    }

    public BarMapsRequest(MapsLocation location, long radius) {
        this.location = location;
        this.radius = radius;
    }

    public MapsLocation getLocation() {
        return location;
    }

    public void setLocation(MapsLocation location) {
        this.location = location;
    }

    public long getRadius() {
        return radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }

    public String locationToString() {
        return location.getLat() + "," + location.getLng();
    }
}
