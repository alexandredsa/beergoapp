package br.com.beergo.domain.dto;

/**
 * Created by alexa on 04/12/2016.
 */
public class MapsGeometry {
    private MapsLocation location;

    public double getLatitude() {
        return location.getLat();
    }

    public double getLongitude() {
        return location.getLng();
    }
}
