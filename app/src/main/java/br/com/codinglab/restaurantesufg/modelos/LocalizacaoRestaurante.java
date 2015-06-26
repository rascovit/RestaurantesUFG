package br.com.codinglab.restaurantesufg.modelos;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public class LocalizacaoRestaurante extends Localizacao{
    String enderecoRestaurante;
    String campus;
    String pontoDeReferencia;

    public LocalizacaoRestaurante(String latitude, String longitude, String enderecoRestaurante, String campus, String pontoDeReferencia) {
        super(latitude, longitude);
        this.enderecoRestaurante = enderecoRestaurante;
        this.campus = campus;
        this.pontoDeReferencia = pontoDeReferencia;
    }

    public String getEnderecoRestaurante() {
        return enderecoRestaurante;
    }

    public String getCampus() {
        return campus;
    }

    public String getPontoDeReferencia() {
        return pontoDeReferencia;
    }
}
