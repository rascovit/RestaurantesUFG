package br.com.codinglab.restaurantesufg.modelos;

import java.util.ArrayList;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public class CardapioAlmoco extends Cardapio {

    private ArrayList<ItemSobremesa> sobremesas;

    public CardapioAlmoco(String diaDaSemana, String data, ArrayList<ItemRefeicao> refeicoes) {
        super(diaDaSemana, data, refeicoes);
        sobremesas = new ArrayList<>();
    }

    public CardapioAlmoco(String diaDaSemana, String data, ArrayList<ItemRefeicao> refeicoes, ArrayList<ItemSobremesa> sobremesas){
        super(diaDaSemana, data, refeicoes);
        this.sobremesas = sobremesas;
    }

    public boolean temSobremesa(){
        return !sobremesas.isEmpty();
    }
}
