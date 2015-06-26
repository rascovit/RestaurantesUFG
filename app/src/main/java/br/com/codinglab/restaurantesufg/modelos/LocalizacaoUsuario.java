package br.com.codinglab.restaurantesufg.modelos;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public class LocalizacaoUsuario extends Localizacao {

    private String campus;

    public LocalizacaoUsuario(String latitude, String longitude, String campus) {
        super(latitude, longitude);
        this.campus = campus;
    }

    public String getCampus() {
        return campus;
    }
}
