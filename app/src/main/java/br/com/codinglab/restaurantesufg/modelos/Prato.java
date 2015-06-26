package br.com.codinglab.restaurantesufg.modelos;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public class Prato {

    private String tipo;
    private String nome;
    private String descricao;

    public Prato(String tipo, String nome, String descricao) {
        this.tipo = tipo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
