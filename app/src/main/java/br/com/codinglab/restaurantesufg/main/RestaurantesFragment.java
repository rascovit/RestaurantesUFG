package br.com.codinglab.restaurantesufg.main;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.codinglab.restaurantesufg.R;
import br.com.codinglab.restaurantesufg.utils.Handler;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantesFragment extends Fragment implements LocationListener {

    private RecyclerView restaurantesRecyclerView;
    private RecyclerView.Adapter restaurantesAdapter;
    private RecyclerView.LayoutManager restaurantesLayoutManager;
    private HashMap<Integer, ArrayList<String>> distanciaTempoAteLocal;
    private LocationManager locationManager;
    private String locationProvider;
    private Location location;
    private Handler handlerRequisicoes;
    private ArrayList<String> nomesRestaurantes;
    private ArrayList<String> tiposRestaurantes;
    private ArrayList<String> valoresRestaurantes;
    private ArrayList<String> coordenadasRestaurantes;
    private ProgressDialog progressDialog;

    public RestaurantesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_restaurantes, container, false);
        handlerRequisicoes = new Handler();
        distanciaTempoAteLocal = new HashMap<>();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        locationProvider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(locationProvider);

        nomesRestaurantes = new ArrayList<>();
        nomesRestaurantes.add("Restaurante Universitário");
        nomesRestaurantes.add("Estação Reuni");
        nomesRestaurantes.add("Fumacinha");
        nomesRestaurantes.add("Pamonharia");

        tiposRestaurantes = new ArrayList<>();
        tiposRestaurantes.add("Por pessoa");
        tiposRestaurantes.add("Prato feito");
        tiposRestaurantes.add("Por pessoa");
        tiposRestaurantes.add("À la carte");

        valoresRestaurantes = new ArrayList<>();
        valoresRestaurantes.add("R$4,50");
        valoresRestaurantes.add("R$8,90");
        valoresRestaurantes.add("R$6,00");
        valoresRestaurantes.add("R$7,90");

        coordenadasRestaurantes = new ArrayList<>();
        coordenadasRestaurantes.add("-16.602032,-49.262235");
        coordenadasRestaurantes.add("-16.603578,-49.265271");
        coordenadasRestaurantes.add("-16.604340,-49.267327");
        coordenadasRestaurantes.add("-16.600898,-49.259088");

        if (location != null) {
            onLocationChanged(location);
            new AsyncTaskBuscaDistancias().execute();
        } else {
            Toast.makeText(getActivity(), "Localização não encontrada", Toast.LENGTH_SHORT).show();
            getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        // Referenciando a RV
        restaurantesRecyclerView = (RecyclerView) rootView.findViewById(R.id.restaurantes_recycler_view);
        // Criando e setando um LayoutManager para a RV
        restaurantesLayoutManager = new LinearLayoutManager(getActivity());
        restaurantesRecyclerView.setLayoutManager(restaurantesLayoutManager);
        return rootView;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    class AsyncTaskBuscaDistancias extends AsyncTask<Void, Void, HashMap<Integer, ArrayList<String>>> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Aguarde um instante...");
            progressDialog.show();
        }

        @Override
        protected HashMap<Integer, ArrayList<String>> doInBackground(Void... params) {
            for (int i = 0; i < coordenadasRestaurantes.size(); i++) {
                String urlPesquisa = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+location.getLatitude()+","+location.getLongitude()+"&destinations="+coordenadasRestaurantes.get(i)+"&mode=driving&language=pt-BR";
                String respostaJSON = handlerRequisicoes.makeServiceCall(urlPesquisa, Handler.GET);
                try {
                    JSONObject jsonObject = new JSONObject(respostaJSON);
                    JSONObject rows = jsonObject.getJSONArray("rows").getJSONObject(0);
                    JSONObject elements = rows.getJSONArray("elements").getJSONObject(0);
                    JSONObject distancia = elements.getJSONObject("distance");
                    String distanciaEmKM = distancia.optString("text");
                    JSONObject tempoAteLocal = elements.getJSONObject("duration");
                    String tempoEstimado = tempoAteLocal.optString("text");
                    ArrayList<String> tempoDistancia = new ArrayList<>();
                    tempoDistancia.add(distanciaEmKM);
                    tempoDistancia.add(tempoEstimado);
                    distanciaTempoAteLocal.put(i, tempoDistancia);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return distanciaTempoAteLocal;
        }

        @Override
        protected void onPostExecute(HashMap<Integer, ArrayList<String>> resposta) {
            progressDialog.dismiss();
            // Criando e especificando um Adapter para a RV
            restaurantesAdapter = new RestaurantesAdapter(getActivity(), nomesRestaurantes, tiposRestaurantes, valoresRestaurantes, distanciaTempoAteLocal);
            restaurantesRecyclerView.setAdapter(restaurantesAdapter);
        }
    }

}
