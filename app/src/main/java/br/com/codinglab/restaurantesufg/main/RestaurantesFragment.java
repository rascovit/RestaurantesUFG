package br.com.codinglab.restaurantesufg.main;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.codinglab.restaurantesufg.R;
import br.com.codinglab.restaurantesufg.modelos.Restaurante;
import br.com.codinglab.restaurantesufg.utils.Handler;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantesFragment extends Fragment implements LocationListener, InterfaceAsyncTask {

    private FragmentManager fragmentManager;
    private RestaurantesAsyncTask restaurantesAsyncTask;
    private RecyclerView restaurantesRecyclerView;
    private RecyclerView.Adapter restaurantesAdapter;
    private RecyclerView.LayoutManager restaurantesLayoutManager;
    private LocationManager locationManager;
    private String locationProvider;
    private Handler handlerRequisicoes;
    ArrayList<Restaurante> listaRestaurantes;
    private int campusId;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurantes, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        toolbar.setElevation(5);

        handlerRequisicoes = new Handler();
        fragmentManager = getActivity().getSupportFragmentManager();

        //INICIALIZAÇÃO DOS SERVIÇOS PARA LOCALIZAÇÃO
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        locationProvider = locationManager.getBestProvider(criteria, false);
        //ATUALIZA O GPS A CADA 5 SEGUNDOS OU A CADA 100 METROS DE MOVIMENTO
        locationManager.requestLocationUpdates(locationProvider, 5000, 10, this);

        campusId = getArguments().getInt("campusId");

        // ASYNCTASK QUE RECUPERA OS RESTAURANTES POR CAMPUS SELECIONADO
        restaurantesAsyncTask = new RestaurantesAsyncTask(getActivity(), this);

        // VERIFICA SE ESTÁ CONECTADO. CASO NÃO, MOSTRA O DIALOG E RETORNA AO FRAGMENT ANTERIOR
        if (estaConectadoInternet()) {
            restaurantesAsyncTask.execute(String.valueOf(campusId));
        }
        else {
            if (getActivity() != null) {
                dialogNaoEstaConectadoInternet().show();
                fragmentManager.popBackStack();
            }
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

    @Override
    public void obtenhaResultadoAsyncTask(ArrayList<Restaurante> restaurantes) {
        listaRestaurantes = restaurantes;
        if (listaRestaurantes.isEmpty() && getActivity() != null) {
            dialogNaoHaRestaurantesCadastrados().show();
            fragmentManager.popBackStack();
        }
        else {
            if (!locationManager.isProviderEnabled(locationProvider) && getActivity() != null) {
                dialogGPSNaoHabilitado().show();
            }
            // Criando e especificando um Adapter para a RV
            restaurantesAdapter = new RestaurantesAdapter(getActivity(), listaRestaurantes);
            restaurantesRecyclerView.setAdapter(restaurantesAdapter);
        }
    }

    // VERIFICA SE O USUÁRIO ESTÁ CONECTADO À INTERNET
    private boolean estaConectadoInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] informacaoRede = connectivity.getAllNetworkInfo();
            if (informacaoRede != null)
                for (int i = 0; i < informacaoRede.length; i++) {
                    if (informacaoRede[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
        }
        return false;
    }

    private Dialog dialogNaoEstaConectadoInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Você não está conectado à internet. Por favor, verifique sua conexão.")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    private Dialog dialogNaoHaRestaurantesCadastrados() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("O campus selecionado não possui restaurantes cadastrados.")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    private Dialog dialogGPSNaoHabilitado() {
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
        return builder.create();
    }

    class AsyncTaskBuscaDistancias extends AsyncTask<Location, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Location... params) {
            if(listaRestaurantes != null){
                for(int i = 0; i < listaRestaurantes.size(); i++){
                    Log.i("GCM Demo","Pesquisando distancia.");
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
                        listaRestaurantes.get(i).getLocalizacaoRestaurante().setTempoViagem(tempoEstimado);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return true;
            }else{
                return false;
            }
        }

        @Override
        // ATUALIZA A LISTA DE RESTAURANTES COM O TEMPO E DISTÂNCIA JÁ CALCULADOS
        protected void onPostExecute(Boolean b) {
            if (restaurantesAdapter != null) {
                restaurantesAdapter.notifyDataSetChanged();
            }
        }
    }

}
