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

import br.com.codinglab.restaurantesufg.R;

/**
 * Created by thiagodurante on 26/06/15.
 */
public class CampusAdapter extends RecyclerView.Adapter<CampusAdapter.ViewHolder> {

    private ArrayList<Integer> campusId;
    private ArrayList<String> listaCampus;
    private ArrayList<String> listaEnderecos;
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
    public CampusAdapter(Context context, ArrayList<String> listaCampus, ArrayList<String> listaEnderecos, ArrayList<Integer> campusId) {
        this.context = context;
        this.listaCampus = listaCampus;
        this.listaEnderecos = listaEnderecos;
        this.campusId = campusId;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CampusAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_campus, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TextView nomeCampus = (TextView) holder.view.findViewById(R.id.nome_do_campus);
        TextView enderecoCampus = (TextView) holder.view.findViewById(R.id.endereco_do_campus);
        Typeface robotoRegular = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
        nomeCampus.setTypeface(robotoRegular);
        enderecoCampus.setTypeface(robotoRegular);
        nomeCampus.setText(listaCampus.get(position).toString());
        enderecoCampus.setText(listaEnderecos.get(position).toString());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantesFragment restaurantesFragment = new RestaurantesFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, restaurantesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCampus.size();
    }
}
