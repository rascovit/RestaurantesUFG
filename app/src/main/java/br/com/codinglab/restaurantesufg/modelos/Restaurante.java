package br.com.codinglab.restaurantesufg.modelos;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public class Restaurante {
    private int id;
    private String nomeRestaurante;
    private double valorMinino;

    private CardapioJantar cardapioJantar;
    private CardapioAlmoco cardapioAlmoco;
    private CardapioCafeDaManha cardapioCafe;

    private boolean temJantar;
    private boolean temAlmoco;
    private boolean temCafeDaManha;

    private LocalizacaoRestaurante localizacaoRestaurante;

    public Restaurante(int id, String nomeRestaurante, double valorMinino, LocalizacaoRestaurante localizacaoRestaurante) {
        this.id = id;
        this.nomeRestaurante = nomeRestaurante;
        this.valorMinino = valorMinino;
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

    public LocalizacaoRestaurante getLocalizacaoRestaurante() {
        return localizacaoRestaurante;
    }

    public CardapioJantar getCardapioJantar() {
        return cardapioJantar;
    }

    public CardapioAlmoco getCardapioAlmoco() {
        return cardapioAlmoco;
    }

    public CardapioCafeDaManha getCardapioCafe() {
        return cardapioCafe;
    }

    public void setCardapioJantar(CardapioJantar cardapioJantar) {
        this.cardapioJantar = cardapioJantar;
        this.temJantar = true;
    }

    public void setCardapioAlmoco(CardapioAlmoco cardapioAlmoco) {
        this.cardapioAlmoco = cardapioAlmoco;
        this.temAlmoco = true;
    }

    public void setCardapioCafe(CardapioCafeDaManha cardapioCafe) {
        this.cardapioCafe = cardapioCafe;
        this.temCafeDaManha = true;
    }

    public void addRefeicaoJantar(ItemRefeicao itemRefeicao){
        cardapioJantar.addRefeicao(itemRefeicao);
    }
    public void addRefeicaoAlmoco(ItemRefeicao itemRefeicao){
        cardapioAlmoco.addRefeicao(itemRefeicao);
    }
    public void addRefeicaoCafe(ItemRefeicao itemRefeicao){
        cardapioCafe.addRefeicao(itemRefeicao);
    }

    public void addSobremesaJantar(ItemSobremesa itemSobremesa){
        cardapioJantar.addSobremesa(itemSobremesa);
    }
    public void addSobremesaAlmoco(ItemSobremesa itemSobremesa){
        cardapioAlmoco.addSobremesa(itemSobremesa);
    }



}
