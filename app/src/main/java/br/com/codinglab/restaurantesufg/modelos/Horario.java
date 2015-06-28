package br.com.codinglab.restaurantesufg.modelos;

/**
 * Created by PC MASTER RACE on 28/06/2015.
 */
public class Horario {
    private String inicio;
    private String fim;

    public Horario(String inicio, String fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFim() {
        return fim;
    }
}
