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

        // Concatenando itensDeCardapio ao ArrayList
        if(restaurante.temCafeDaManha()){
            for(int i=0; i<restaurante.getCardapioCafe().size(); i++){
                for(int j=0; j<restaurante.getCardapioCafe().get(i).getRefeicoes().size(); j++){
                    itensCardapio.add(restaurante.getCardapioCafe().get(i).getRefeicoes().get(j));
                }
            }
        }
        if(restaurante.temAlmoco()){
            for(int i=0; i<restaurante.getCardapioAlmoco().size(); i++){
                for(int j=0; j<restaurante.getCardapioAlmoco().get(i).getRefeicoes().size(); j++){
                    itensCardapio.add(restaurante.getCardapioAlmoco().get(i).getRefeicoes().get(j));
                }
                if(restaurante.getCardapioAlmoco().get(i).temSobremesa()){
                    for(int j=0; j<restaurante.getCardapioAlmoco().get(i).getSobremesas().size(); j++){
                        itensCardapio.add(restaurante.getCardapioAlmoco().get(i).getSobremesas().get(j));
                    }
                }
            }
        }
        if(restaurante.temJantar()){
            for(int i=0; i<restaurante.getCardapioJantar().size(); i++){
                for(int j=0; j<restaurante.getCardapioJantar().get(i).getRefeicoes().size(); j++){
                    itensCardapio.add(restaurante.getCardapioJantar().get(i).getRefeicoes().get(j));
                }
                if(restaurante.getCardapioJantar().get(i).temSobremesa()){
                    for(int j=0; j<restaurante.getCardapioJantar().get(i).getSobremesas().size(); j++){
                        itensCardapio.add(restaurante.getCardapioJantar().get(i).getSobremesas().get(j));
                    }
                }
            }
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
        precoDoPratoTextView.setText("R$ " +Double.toString(itensCardapio.get(position).getPrecoPrato()));
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
