package br.com.codinglab.restaurantesufg.modelos;

/**
 * Created by PC MASTER RACE on 02/07/2015.
 */
public class Campus {
    private String campus;
    private int campusId;
    private String enderecoCampus;

    public Campus(String campus, int campusId, String enderecoCampus) {
        this.campus = campus;
        this.campusId = campusId;
        this.enderecoCampus = enderecoCampus;
    }


    public String getCampus() {
        return campus;
    }

    public int getCampusId() {
        return campusId;
    }

    public String getEnderecoCampus() {
        return enderecoCampus;
    }
}
