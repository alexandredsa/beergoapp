package br.com.beergo.domain.dto;

import com.google.gson.annotations.SerializedName;

public class MapsResult {
    @SerializedName("name")
    private String nome;
    @SerializedName("rating")
    private float avaliacao;
    @SerializedName("opening_hours")
    private MapsOpeningHours disponibilidade;
    @SerializedName("geometry")
    private MapsGeometry coordenadas;

    public String getNome() {
        return nome;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public boolean isAberto(){
        return disponibilidade.isOpen();
    }

    public MapsGeometry getCoordenadas() {
        return coordenadas;
    }
}
