package br.com.codinglab.restaurantesufg.modelos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public class Restaurante implements Serializable{
    private int id;
    private String nomeRestaurante;
    private double valorMinino;
    private String estiloDeServir;

    private Horario horarioCafe;
    private Horario horarioAlmoco;
    private Horario horarioJantar;


    private ArrayList<CardapioJantar> cardapioJantar;
    private ArrayList<CardapioAlmoco> cardapioAlmoco;
    private ArrayList<CardapioCafeDaManha> cardapioCafe;

    private boolean temJantar;
    private boolean temAlmoco;
    private boolean temCafeDaManha;

    private LocalizacaoRestaurante localizacaoRestaurante;

    public Restaurante(int id, String nomeRestaurante, double valorMinino,String estiloDeServir, LocalizacaoRestaurante localizacaoRestaurante) {
        this.id = id;
        this.nomeRestaurante = nomeRestaurante;
        this.valorMinino = valorMinino;
        this.estiloDeServir = estiloDeServir;
        this.localizacaoRestaurante = localizacaoRestaurante;
        this.temJantar = false;
        this.temAlmoco = false;
        this.temCafeDaManha = false;
    }

    public int getId() {
        return id;

    }

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }

    public double getValorMinino() {
        return valorMinino;
    }

    public String getEstiloDeServir() {
        return estiloDeServir;
    }

    public LocalizacaoRestaurante getLocalizacaoRestaurante() {
        return localizacaoRestaurante;
    }

    public ArrayList<CardapioJantar> getCardapioJantar() { return cardapioJantar;}

    public ArrayList<CardapioAlmoco> getCardapioAlmoco() {
        return cardapioAlmoco;
    }

    public ArrayList<CardapioCafeDaManha> getCardapioCafe() {
        return cardapioCafe;
    }

    public Horario getHorarioCafe() {
        return horarioCafe;
    }

    public Horario getHorarioAlmoco() {
        return horarioAlmoco;
    }

    public Horario getHorarioJantar() {
        return horarioJantar;
    }

    public boolean temJantar() {
        return temJantar;
    }

    public boolean temAlmoco() {
        return temAlmoco;
    }

    public boolean temCafeDaManha() {
        return temCafeDaManha;
    }

    public void setHorarioCafe(Horario horarioCafe) {
        this.horarioCafe = horarioCafe;
    }

    public void setHorarioAlmoco(Horario horarioAlmoco) {
        this.horarioAlmoco = horarioAlmoco;
    }

    public void setHorarioJantar(Horario horarioJantar) {
        this.horarioJantar = horarioJantar;
    }

    public void setCardapioJantar(ArrayList<CardapioJantar> cardapioJantar) {
        this.cardapioJantar = cardapioJantar;
        this.temJantar = true;
    }

    public void setCardapioAlmoco(ArrayList<CardapioAlmoco> cardapioAlmoco) {
        this.cardapioAlmoco = cardapioAlmoco;
        this.temAlmoco = true;
    }

    public void setCardapioCafe(ArrayList<CardapioCafeDaManha> cardapioCafe) {
        this.cardapioCafe = cardapioCafe;
        this.temCafeDaManha = true;
    }
}
