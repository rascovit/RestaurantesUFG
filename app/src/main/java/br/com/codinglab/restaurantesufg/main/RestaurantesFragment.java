package br.com.codinglab.restaurantesufg.main;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import br.com.codinglab.restaurantesufg.R;
import br.com.codinglab.restaurantesufg.modelos.Restaurante;
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
    private Handler handlerRequisicoes;
    ArrayList<Restaurante> listaRestaurantes;
    private ArrayList<String> nomesRestaurantes;
    private ArrayList<String> tiposRestaurantes;
    private ArrayList<String> valoresRestaurantes;
    private ArrayList<String> coordenadasRestaurantes;
    private ArrayList<String> tempoDistancia;
    private ArrayList<Integer> campusId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurantes, container, false);

        handlerRequisicoes = new Handler();
        distanciaTempoAteLocal = new HashMap<>();
        tempoDistancia = new ArrayList<>();

        //INICIALIZAÇÃO DOS SERVIÇOS PARA LOCALIZAÇÃO
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        locationProvider = locationManager.getBestProvider(criteria, false);
        //ATUALIZA O GPS A CADA 5 SEGUNDOS OU A CADA 100 METROS
        locationManager.requestLocationUpdates(locationProvider, 5000, 100, this);

        if (!locationManager.isProviderEnabled(locationProvider)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Localização");
            builder.setMessage("Não foi possível acessar os dados de localização do seu dispositivo. Deseja ativá-los?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            builder.setNegativeButton("Não", null);
            builder.show();
        }
        try {

            listaRestaurantes = new RestaurantesAsyncTask(getActivity()).execute("campus2").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //MOCK DE RESTAURANTES
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

        //INICIALIZA AS DISTANCIAS E OS TEMPOS ATÉ OS RESTAURANTES COMO VAZIO
        for (int i = 0; i < coordenadasRestaurantes.size(); i++) {
            tempoDistancia.add("");
            tempoDistancia.add("");
            distanciaTempoAteLocal.put(i, tempoDistancia);
        }

        // Referenciando a RV
        restaurantesRecyclerView = (RecyclerView) rootView.findViewById(R.id.restaurantes_recycler_view);
        // Criando e setando um LayoutManager para a RV
        restaurantesLayoutManager = new LinearLayoutManager(getActivity());
        restaurantesRecyclerView.setLayoutManager(restaurantesLayoutManager);
        // Criando e especificando um Adapter para a RV
        restaurantesAdapter = new RestaurantesAdapter(getActivity(), nomesRestaurantes, tiposRestaurantes, valoresRestaurantes, distanciaTempoAteLocal, listaRestaurantes);
        restaurantesRecyclerView.setAdapter(restaurantesAdapter);

        return rootView;
    }

    @Override
    public void onLocationChanged(Location location) {
        //AO OBTER ALGUMA ATUALIZAÇÃO DE LOCALIZAÇÃO, EXECUTA A ASYNCTASK PARA OBTER DISTANCIAS E TEMPOS ESTIMADOS
        new AsyncTaskBuscaDistancias().execute(location);
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

    class AsyncTaskBuscaDistancias extends AsyncTask<Location, Void, HashMap<Integer, ArrayList<String>>> {

        @Override
        protected HashMap<Integer, ArrayList<String>> doInBackground(Location... params) {
            //LIMPA A LISTA DE TEMPO E DISTANCIA
            tempoDistancia.clear();
            //LIMPA O MAPA DE TEMPOS E DISTANCIAS DOS RESTAURANTES
            distanciaTempoAteLocal.clear();


            for(int i = 0; i < listaRestaurantes.size(); i++){
                String par = listaRestaurantes.get(i).getLocalizacaoRestaurante().getLatitude() + "," + listaRestaurantes.get(i).getLocalizacaoRestaurante().getLongitude();
                String urlPesquisa = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + params[0].getLatitude() + "," + params[0].getLongitude() + "&destinations=" + par + "&mode=driving&language=pt-BR";
                String respostaJSON = handlerRequisicoes.makeServiceCall(urlPesquisa, Handler.GET);
                try {

                    JSONObject jsonObject = new JSONObject(respostaJSON);
                    JSONObject rows = jsonObject.getJSONArray("rows").getJSONObject(0);
                    JSONObject elements = rows.getJSONArray("elements").getJSONObject(0);
                    JSONObject distancia = elements.getJSONObject("distance");
                    String distanciaEmKM = distancia.optString("text");
                    JSONObject tempoAteLocal = elements.getJSONObject("duration");
                    String tempoEstimado = tempoAteLocal.optString("text");

                    listaRestaurantes.get(i).getLocalizacaoRestaurante().setDistancia(distanciaEmKM);
                    listaRestaurantes.get(i).getLocalizacaoRestaurante().setTempViagem(tempoEstimado);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }






            /*for (int i = 0; i < coordenadasRestaurantes.size(); i++) {
                String urlPesquisa = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + params[0].getLatitude() + "," + params[0].getLongitude() + "&destinations=" + coordenadasRestaurantes.get(i) + "&mode=driving&language=pt-BR";
                String respostaJSON = handlerRequisicoes.makeServiceCall(urlPesquisa, Handler.GET);
                try {
                    JSONObject jsonObject = new JSONObject(respostaJSON);
                    JSONObject rows = jsonObject.getJSONArray("rows").getJSONObject(0);
                    JSONObject elements = rows.getJSONArray("elements").getJSONObject(0);
                    JSONObject distancia = elements.getJSONObject("distance");
                    String distanciaEmKM = distancia.optString("text");
                    JSONObject tempoAteLocal = elements.getJSONObject("duration");
                    String tempoEstimado = tempoAteLocal.optString("text");
                    tempoDistancia.add(distanciaEmKM);
                    tempoDistancia.add(tempoEstimado);
                    distanciaTempoAteLocal.put(i, tempoDistancia);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/
            return distanciaTempoAteLocal;
        }

        @Override
        protected void onPostExecute(HashMap<Integer, ArrayList<String>> resposta) {
            restaurantesAdapter.notifyDataSetChanged();
        }
    }

}
