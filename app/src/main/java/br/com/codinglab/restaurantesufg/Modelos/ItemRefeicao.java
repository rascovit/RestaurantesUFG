package br.com.codinglab.restaurantesufg.modelos;

import java.util.List;

/**
 * Created by alunoinf on 20/06/2015.
 */
public class ItemRefeicao {

    private String diaDaSemana;
    private String data;
    private List<Prato> pratosDoDia;

    public ItemRefeicao(String diaDaSemana, String data, List<Prato> pratosDoDia) {
        this.diaDaSemana = diaDaSemana;
        this.data = data;
        this.pratosDoDia = pratosDoDia;
    }

    public List<Prato> getPratosDoDia() {
        return pratosDoDia;
    }

    public String getData() {
        return data;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }
}
