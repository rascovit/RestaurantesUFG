package br.com.codinglab.restaurantesufg.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import br.com.codinglab.restaurantesufg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends Fragment {

    private SupportMapFragment mapaFragment;
    private GoogleMap mapaRestaurantes;

    public MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);
        mapaFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapaRestaurante);
        mapaRestaurantes = mapaFragment.getMap();
        return rootView;
    }

    @Override
    public void onDestroy() {

    }
}
