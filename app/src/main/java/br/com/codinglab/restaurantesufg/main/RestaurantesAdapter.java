package br.com.codinglab.restaurantesufg.main;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.codinglab.restaurantesufg.R;
import br.com.codinglab.restaurantesufg.modelos.Restaurante;
import br.com.codinglab.restaurantesufg.tabs.SlidingTabsBasicFragment;

/**
 * Created by thiagodurante on 26/06/15.
 */
public class RestaurantesAdapter extends RecyclerView.Adapter<RestaurantesAdapter.ViewHolder> {

    private ArrayList<Restaurante> restaurantes;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RestaurantesAdapter(Context context, ArrayList<Restaurante> restaurantes) {
        this.context = context;
        this.restaurantes = restaurantes;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RestaurantesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurante, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TextView nomeRestaurante = (TextView) holder.view.findViewById(R.id.textViewNomeRestaurante);
        TextView tipoRestaurante = (TextView) holder.view.findViewById(R.id.textViewTipoRestaurante);
        TextView valorMinimoRestaurante = (TextView) holder.view.findViewById(R.id.textViewValorMinimoRestaurante);
        TextView distanciaRestaurante = (TextView) holder.view.findViewById(R.id.textViewDistanciaRestaurante);
        Typeface robotoRegular = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");

        nomeRestaurante.setText(restaurantes.get(position).getNomeRestaurante());
        tipoRestaurante.setText(restaurantes.get(position).getEstiloDeServir());
        valorMinimoRestaurante.setText("Refeições a partir de R$ " + String.valueOf(restaurantes.get(position).getValorMinino()));
        distanciaRestaurante.setText(restaurantes.get(position).getLocalizacaoRestaurante().getDistancia() + "  " + restaurantes.get(position).getLocalizacaoRestaurante().getTempoViagem());

        nomeRestaurante.setTypeface(robotoRegular);
        tipoRestaurante.setTypeface(robotoRegular);
        valorMinimoRestaurante.setTypeface(robotoRegular);
        distanciaRestaurante.setTypeface(robotoRegular);

        // TABS
        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SlidingTabsBasicFragment slidingTabsBasicFragment = new SlidingTabsBasicFragment();
                Bundle informacoes = new Bundle();
                String[] coordenadas = new String[]{restaurantes.get(position).getLocalizacaoRestaurante().getLatitude(),
                        restaurantes.get(position).getLocalizacaoRestaurante().getLongitude()};
                informacoes.putStringArray("coordenadasRestaurante", coordenadas);
                informacoes.putString("nomeRestaurante", restaurantes.get(position).getNomeRestaurante());
                informacoes.putString("enderecoRestaurante", restaurantes.get(position).getLocalizacaoRestaurante().getEnderecoRestaurante());
                informacoes.putSerializable("objetoRestaurante", restaurantes.get(position));

                slidingTabsBasicFragment.setArguments(informacoes);

                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, slidingTabsBasicFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //SHAREDPREFERENCES PRA SETAR TUDO COMO NÃO FAVORITO

    }

    @Override
    public int getItemCount() {
        return restaurantes.size();
    }
}
