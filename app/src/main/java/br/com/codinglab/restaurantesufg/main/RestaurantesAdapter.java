package br.com.codinglab.restaurantesufg.main;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.codinglab.restaurantesufg.R;
import br.com.codinglab.restaurantesufg.tabs.SlidingTabsBasicFragment;

/**
 * Created by thiagodurante on 26/06/15.
 */
public class RestaurantesAdapter extends RecyclerView.Adapter<RestaurantesAdapter.ViewHolder> {

    private ArrayList<String> listaNomes;
    private ArrayList<String> listaTipos;
    private ArrayList<String> listaValores;
    private HashMap<Integer, ArrayList<String>> listaDistanciasTempos;
    private Context context;
    private static final int DISTANCIA_ATE_RESTAURANTE = 0;
    private static final int TEMPO_ATE_RESTAURANTE = 1;

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
    public RestaurantesAdapter(Context context, ArrayList<String> listaNomes, ArrayList<String> listaTipos, ArrayList<String> listaValores,
                               HashMap<Integer, ArrayList<String>> listaDistanciasTempos) {
        this.context = context;
        this.listaNomes = listaNomes;
        this.listaTipos = listaTipos;
        this.listaValores = listaValores;
        this.listaDistanciasTempos = listaDistanciasTempos;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RestaurantesAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                             int viewType) {
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

        nomeRestaurante.setText(listaNomes.get(position).toString());
        tipoRestaurante.setText(listaTipos.get(position).toString());
        valorMinimoRestaurante.setText(listaValores.get(position).toString());
        distanciaRestaurante.setText(listaDistanciasTempos.get(position).get(DISTANCIA_ATE_RESTAURANTE) + "  " + listaDistanciasTempos.get(position).get(TEMPO_ATE_RESTAURANTE));

        nomeRestaurante.setTypeface(robotoRegular);
        tipoRestaurante.setTypeface(robotoRegular);
        valorMinimoRestaurante.setTypeface(robotoRegular);
        distanciaRestaurante.setTypeface(robotoRegular);

        // TABS
        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SlidingTabsBasicFragment slidingTabsBasicFragment = new SlidingTabsBasicFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, slidingTabsBasicFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaNomes.size();
    }
}
