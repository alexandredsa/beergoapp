package br.com.beergo.domain.dto;

public class MapsLocation {
    private double lat, lng;

    public MapsLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public MapsLocation() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}

