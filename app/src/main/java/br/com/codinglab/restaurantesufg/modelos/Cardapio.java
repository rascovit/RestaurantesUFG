package br.com.codinglab.restaurantesufg.modelos;

import java.util.ArrayList;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public abstract class Cardapio {

    private String diaDaSemana;
    private String data;
    private ArrayList<ItemRefeicao> refeicoes;

    public Cardapio(String diaDaSemana, String data, ArrayList<ItemRefeicao> refeicoes) {
        this.diaDaSemana = diaDaSemana;
        this.data = data;
        this.refeicoes = refeicoes;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public String getData() {
        return data;
    }

    public ArrayList<ItemRefeicao> getRefeicoes() {
        return refeicoes;
    }
}
