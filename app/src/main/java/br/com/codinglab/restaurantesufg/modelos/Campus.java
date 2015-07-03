package br.com.codinglab.restaurantesufg.modelos;

/**
 * Created by PC MASTER RACE on 02/07/2015.
 * Classe que visa representar a entidade campus.
 */
public class Campus {
    private String campus;
    private int campusId;
    private LocalizacaoCampus localizacaoCampus;

    public Campus(String campus, int campusId, String enderecoCampus) {
        this.campus = campus;
        this.campusId = campusId;
        this.localizacaoCampus = new LocalizacaoCampus(enderecoCampus);
    }

    public String getCampus() {
        return campus;
    }

    public int getCampusId() {
        return campusId;
    }

    public String getEnderecoCampus() {
        return localizacaoCampus.getEndereco();
    }
}
