package br.com.codinglab.restaurantesufg.tabs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.codinglab.restaurantesufg.R;
import br.com.codinglab.restaurantesufg.modelos.ItemCardapio;
import br.com.codinglab.restaurantesufg.modelos.ItemRefeicao;
import br.com.codinglab.restaurantesufg.modelos.Restaurante;

/**
 * Created by thiagodurante on 03/07/15.
 */
public class CardapioAdapter extends RecyclerView.Adapter<CardapioAdapter.ViewHolder>{

    //--------------- INICIO VERIFICAÇÃO

    private Context context;
    private Restaurante restaurante;
    private ArrayList<ItemCardapio> itensCardapio = new ArrayList<>();

    public CardapioAdapter(Restaurante restaurante, Context context) {
        this.restaurante = restaurante;
        this.context = context;

        boolean cafeTerminou = false;
        boolean almocoTerminou = false;
        boolean jantarTerminou = false;
        int cardapioContador = 0;
        while(!cafeTerminou || !almocoTerminou || !jantarTerminou){
            if(restaurante.temCafeDaManha()){
                if(cardapioContador < restaurante.getCardapioCafe().size()){
                    ArrayList<ItemRefeicao> listaRefeicoes = restaurante.getCardapioCafe().get(cardapioContador).getRefeicoes();
                    String dataDeServir = restaurante.getCardapioCafe().get(cardapioContador).getData();
                    for(int j=0; j<restaurante.getCardapioCafe().get(cardapioContador).getRefeicoes().size(); j++){
                        listaRefeicoes.get(j).setServidoNo("Café da manhã");
                        listaRefeicoes.get(j).setServidoDia(dataDeServir);
                        itensCardapio.add(listaRefeicoes.get(j));
                    }
            }else{
                cafeTerminou = true;
            }
            }else{
                cafeTerminou = true;
            }

            if(restaurante.temAlmoco()){
                if(cardapioContador < restaurante.getCardapioAlmoco().size()){
                    for(int j=0; j<restaurante.getCardapioAlmoco().get(cardapioContador).getRefeicoes().size(); j++){
                        restaurante.getCardapioAlmoco().get(cardapioContador).getRefeicoes().get(j).setServidoNo("Almoço");
                        restaurante.getCardapioAlmoco().get(cardapioContador).getRefeicoes().get(j).setServidoDia(restaurante.getCardapioAlmoco().get(cardapioContador).getData());
                        itensCardapio.add(restaurante.getCardapioAlmoco().get(cardapioContador).getRefeicoes().get(j));
                    }
                    for(int j=0; j<restaurante.getCardapioAlmoco().get(cardapioContador).getSobremesas().size(); j++){
                        restaurante.getCardapioAlmoco().get(cardapioContador).getSobremesas().get(j).setServidoNo("Sobremesa");
                        restaurante.getCardapioAlmoco().get(cardapioContador).getSobremesas().get(j).setServidoDia(restaurante.getCardapioAlmoco().get(cardapioContador).getData());
                        itensCardapio.add(restaurante.getCardapioAlmoco().get(cardapioContador).getSobremesas().get(j));
                    }
                }else{
                    almocoTerminou = true;
                }
            }else{
                almocoTerminou = true;
            }

            if(restaurante.temJantar()){
                if(cardapioContador < restaurante.getCardapioJantar().size()){
                    for(int j=0; j < restaurante.getCardapioJantar().get(cardapioContador).getRefeicoes().size(); j++){
                        restaurante.getCardapioJantar().get(cardapioContador).getRefeicoes().get(j).setServidoNo("Jantar");
                        restaurante.getCardapioJantar().get(cardapioContador).getRefeicoes().get(j).setServidoDia(restaurante.getCardapioJantar().get(cardapioContador).getData());
                        itensCardapio.add(restaurante.getCardapioJantar().get(cardapioContador).getRefeicoes().get(j));
                    }

                    for(int j=0; j<restaurante.getCardapioJantar().get(cardapioContador).getSobremesas().size(); j++){
                        restaurante.getCardapioJantar().get(cardapioContador).getSobremesas().get(j).setServidoNo("Sobremesa");
                        restaurante.getCardapioJantar().get(cardapioContador).getSobremesas().get(j).setServidoDia(restaurante.getCardapioJantar().get(cardapioContador).getData());
                        itensCardapio.add(restaurante.getCardapioJantar().get(cardapioContador).getSobremesas().get(j));
                    }
                }else{
                    jantarTerminou = true;
                }

            }else{
                jantarTerminou = true;
            }
            cardapioContador++;

        }
    }

    //--------------- FIM VERIFICAÇÃO


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    @Override
    public CardapioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardapio, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardapioAdapter.ViewHolder holder, int position) {

        Typeface robotoRegular = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");

        TextView nomeDoPratoTextView = (TextView) holder.view.findViewById(R.id.nomeDoPrato_textView);
        TextView descricaoDoPratoTextView = (TextView) holder.view.findViewById(R.id.descricaoDoPrato_textView);
        TextView precoDoPratoTextView = (TextView) holder.view.findViewById(R.id.precoDoPrato_textView);
        TextView tipoDoPratoTextView = (TextView) holder.view.findViewById(R.id.tipoDoPrato_textView);

        nomeDoPratoTextView.setText(itensCardapio.get(position).getPrato().getNome());
        descricaoDoPratoTextView.setText(itensCardapio.get(position).getPrato().getDescricao());
        precoDoPratoTextView.setText("R$ " +Double.toString(itensCardapio.get(position).getPrecoPrato()) + " -  " +itensCardapio.get(position).getServidoDia() + " - " +itensCardapio.get(position).getServidoNo());
        tipoDoPratoTextView.setText(itensCardapio.get(position).getPrato().getTipo());

        nomeDoPratoTextView.setTypeface(robotoRegular);
        descricaoDoPratoTextView.setTypeface(robotoRegular);
        precoDoPratoTextView.setTypeface(robotoRegular);
        tipoDoPratoTextView.setTypeface(robotoRegular);

    }

    @Override
    public int getItemCount() {
        return itensCardapio.size();
    }


}
