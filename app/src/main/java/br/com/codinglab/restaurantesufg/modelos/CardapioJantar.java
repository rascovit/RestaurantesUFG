package br.com.codinglab.restaurantesufg.modelos;

import java.util.ArrayList;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public class CardapioJantar extends Cardapio {

    private ArrayList<ItemSobremesa> sobremesas;
    private boolean sobremesasDisponiveis;

    public CardapioJantar(String diaDaSemana, String data, ArrayList<ItemRefeicao> refeicoes) {
        super(diaDaSemana, data, refeicoes);
        sobremesasDisponiveis = false;
    }

    public CardapioJantar(String diaDaSemana, String data, ArrayList<ItemRefeicao> refeicoes, ArrayList<ItemSobremesa> sobremesas){
        super(diaDaSemana, data, refeicoes);
        this.sobremesas = sobremesas;
        sobremesasDisponiveis = true;
    }

    public void addSobremesa(ItemSobremesa itemSobremesa ){
        sobremesas.add(itemSobremesa);
    }

    public boolean temSobremesa(){
        return sobremesasDisponiveis;
    }
}
