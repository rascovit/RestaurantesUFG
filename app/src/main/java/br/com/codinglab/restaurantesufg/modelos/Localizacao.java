package br.com.codinglab.restaurantesufg.modelos;

import java.io.Serializable;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public abstract class Localizacao implements Serializable {
    private String latitude;
    private String longitude;
    private String endereco;

    public Localizacao(String latitude, String longitude, String endereco) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.endereco = endereco;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getEndereco() {
        return endereco;
    }
}
