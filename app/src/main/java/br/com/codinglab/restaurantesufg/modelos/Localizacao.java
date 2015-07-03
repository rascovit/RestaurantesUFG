package br.com.codinglab.restaurantesufg.modelos;

import java.io.Serializable;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public abstract class Localizacao implements Serializable {
    private String latitude;
    private String longitude;

    public Localizacao(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
