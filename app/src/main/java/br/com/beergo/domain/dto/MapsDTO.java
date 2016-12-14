package br.com.beergo.domain.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandre on 13/12/16.
 */

public class MapsDTO {
    @SerializedName("percent_off")
    private double percentOff;
    private MapsLocation location;
    private String name;
    private float rating;
    @SerializedName("is_open")
    private boolean isOpen;

    public double getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(double percentOff) {
        this.percentOff = percentOff;
    }

    public MapsLocation getLocation() {
        return location;
    }

    public void setLocation(MapsLocation location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }


    public String snippet() {
        StringBuilder sb = new StringBuilder()
                .append("<b>" + name + "</b><br>")
                .append("<b>Desconto de até: " + String.format("%.2f", percentOff) + "%</b><br>")
                .append("<b>Avaliação: </b>")
                .append(String.valueOf(rating))
                .append("<br>")
                .append(isOpen() ? "<b>Aberto</b>" : "<b>Fechado</b>");
        return sb.toString();
    }
}
