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
    @SerializedName("percent_desconto")
    private int percentDesconto;

    public String getNome() {
        return nome;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public boolean isAberto() {
        if (disponibilidade == null)
            return false;

        return disponibilidade.isOpen();
    }

    public MapsGeometry getCoordenadas() {
        return coordenadas;
    }

    public String snippet() {
        StringBuilder sb = new StringBuilder()
                .append("<b>" + nome + "</b><br>")
                .append("<b>Desconto de até: 42%</b><br>")
                .append("<b>Avaliação: </b>")
                .append(String.valueOf(avaliacao))
                .append("<br>")
                .append(isAberto() ? "<b>Aberto</b>" : "<b>Fechado</b>");
        return sb.toString();
    }
}
