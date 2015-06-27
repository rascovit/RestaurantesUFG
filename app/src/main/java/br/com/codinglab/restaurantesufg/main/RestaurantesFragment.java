package br.com.codinglab.restaurantesufg.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.codinglab.restaurantesufg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantesFragment extends Fragment {

    private RecyclerView restaurantesRecyclerView;
    private RecyclerView.Adapter restaurantesAdapter;
    private RecyclerView.LayoutManager restaurantesLayoutManager;

    public RestaurantesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_restaurantes, container, false);

        ArrayList<String> nomesRestaurantes = new ArrayList<>();
        nomesRestaurantes.add("Restaurante Universitário");
        nomesRestaurantes.add("Estação Reuni");
        nomesRestaurantes.add("Fumacinha");
        nomesRestaurantes.add("Pamonharia");

        ArrayList<String> tiposRestaurantes = new ArrayList<>();
        tiposRestaurantes.add("Por pessoa");
        tiposRestaurantes.add("Prato feito");
        tiposRestaurantes.add("Por pessoa");
        tiposRestaurantes.add("À la carte");

        ArrayList<String> valoresRestaurantes = new ArrayList<>();
        valoresRestaurantes.add("R$4,50");
        valoresRestaurantes.add("R$8,90");
        valoresRestaurantes.add("R$6,00");
        valoresRestaurantes.add("R$7,90");

        ArrayList<String> distanciasRestaurantes = new ArrayList<>();
        distanciasRestaurantes.add("aprox. 1km");
        distanciasRestaurantes.add("aprox. 1.2km");
        distanciasRestaurantes.add("aprox. 1.9km");
        distanciasRestaurantes.add("aprox. 2.8km");

        // Referenciando a RV
        restaurantesRecyclerView = (RecyclerView) rootView.findViewById(R.id.restaurantes_recycler_view);

        // Criando e setando um LayoutManager para a RV
        restaurantesLayoutManager = new LinearLayoutManager(getActivity());
        restaurantesRecyclerView.setLayoutManager(restaurantesLayoutManager);

        // Criando e especificando um Adapter para a RV
        restaurantesAdapter = new RestaurantesAdapter(getActivity(), nomesRestaurantes, tiposRestaurantes, valoresRestaurantes, distanciasRestaurantes);
        restaurantesRecyclerView.setAdapter(restaurantesAdapter);

        return rootView;
    }


}
