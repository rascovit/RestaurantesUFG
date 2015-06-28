package br.com.codinglab.restaurantesufg.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import br.com.codinglab.restaurantesufg.modelos.CardapioAlmoco;
import br.com.codinglab.restaurantesufg.modelos.CardapioCafeDaManha;
import br.com.codinglab.restaurantesufg.modelos.CardapioJantar;
import br.com.codinglab.restaurantesufg.modelos.Horario;
import br.com.codinglab.restaurantesufg.modelos.ItemRefeicao;
import br.com.codinglab.restaurantesufg.modelos.ItemSobremesa;
import br.com.codinglab.restaurantesufg.modelos.LocalizacaoRestaurante;
import br.com.codinglab.restaurantesufg.modelos.Prato;
import br.com.codinglab.restaurantesufg.modelos.Restaurante;

/**
 * Created by PC MASTER RACE on 28/06/2015.
 */
public class RestaurantesAsyncTask extends AsyncTask<String,Void,ArrayList<Restaurante>> {

    @Override
    protected ArrayList<Restaurante> doInBackground(String... params) {

        String campus = params[0];
        String url = "http://codinglab.com.br/samuel/restaurantes.php?campus=" + campus;
        String jsonResposta = "";
        ArrayList<Restaurante> listaRestaurantes = new ArrayList<>();


        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                jsonResposta = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
            }else{
                // req HTTP deu errado
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(jsonResposta != ""){

            try {
                JSONObject jsonPrincipal = new JSONObject(jsonResposta);
                String serverStatus = jsonPrincipal.getString("status");

                if(serverStatus == "OK"){



                    JSONArray jsonArrayRestaurantes = jsonPrincipal.getJSONArray("restaurantes");

                    for(int i = 0; i < jsonArrayRestaurantes.length(); i++) {
                        JSONObject jsonRestaurante = jsonArrayRestaurantes.getJSONObject(i);


                        int idRestaurante = jsonRestaurante.getInt("id");
                        String nomeRestaurante = jsonRestaurante.getString("nome");
                        double valorMinimo = jsonRestaurante.getDouble("valor-minimo");


                        JSONObject jsonObject = jsonRestaurante.getJSONObject("localizacao");
                        String endereco = jsonObject.getString("endereco");
                        String campusRestaurante = jsonObject.getString("campus");
                        String pontoReferencia = jsonObject.getString("ponto-de-referencia");
                        String latitude = String.valueOf(jsonObject.getString("latitude"));
                        String longitude = String.valueOf(jsonObject.getString("longitude"));


                        LocalizacaoRestaurante localizacaoRestaurante = new LocalizacaoRestaurante(latitude, longitude, endereco, campusRestaurante, pontoReferencia);
                        Restaurante restaurante = new Restaurante(idRestaurante, nomeRestaurante, valorMinimo, localizacaoRestaurante);



                        jsonObject = jsonRestaurante.getJSONObject("horarios");

                        if (!jsonObject.isNull("cafe-da-manha")) {

                            String inicio = jsonObject.getJSONObject("cafe-da-manha").getString("inicio");
                            String fim = jsonObject.getJSONObject("cafe-da-manha").getString("fim");
                            restaurante.setHorarioCafe(new Horario(inicio, fim));


                            JSONArray cardapioCafeDaManha = jsonRestaurante.getJSONObject("cardapio").getJSONArray("cafe-da-manha");

                            ArrayList<CardapioCafeDaManha> cardapioCafe = new ArrayList<>();

                            for (int k = 0; k < cardapioCafeDaManha.length(); k++) {

                                JSONObject jsonCardapioDia = cardapioCafeDaManha.getJSONObject(k);
                                String diaDaSemana = jsonCardapioDia.getString("dia-da-semana");
                                String data = jsonCardapioDia.getString("data");

                                JSONArray jsonArrayRefeicoesDia = jsonCardapioDia.getJSONArray("refeicoes");

                                ArrayList<ItemRefeicao> listaRefeicoes = new ArrayList<>();

                                for (int j = 0; j < jsonArrayRefeicoesDia.length(); j++) {
                                    JSONObject refeicao = jsonArrayRefeicoesDia.getJSONObject(j);
                                    double precoRefeicao = refeicao.getDouble("preco");
                                    String nomePrato = refeicao.getJSONObject("prato").getString("nome");
                                    String descricaoPrato = refeicao.getJSONObject("prato").getString("descricao");
                                    String tipoPrato = refeicao.getJSONObject("prato").getString("tipo");
                                    listaRefeicoes.add(new ItemRefeicao(precoRefeicao, new Prato(nomePrato, descricaoPrato, tipoPrato)));
                                }

                                cardapioCafe.add(new CardapioCafeDaManha(diaDaSemana, data, listaRefeicoes));

                            }

                            restaurante.setCardapioCafe(cardapioCafe);

                        }
                        if (!jsonObject.isNull("almoco")) {

                            String inicio = jsonObject.getJSONObject("almoco").getString("inicio");
                            String fim = jsonObject.getJSONObject("almoco").getString("fim");
                            restaurante.setHorarioAlmoco(new Horario(inicio, fim));


                            JSONArray jsonCardapioAlmoco = jsonRestaurante.getJSONObject("cardapio").getJSONArray("almoco");

                            ArrayList<CardapioAlmoco> cardapioAlmoco = new ArrayList<>();

                            for (int k = 0; k < jsonCardapioAlmoco.length(); k++) {

                                JSONObject jsonCardapioDia = jsonCardapioAlmoco.getJSONObject(k);
                                String diaDaSemana = jsonCardapioDia.getString("dia-da-semana");
                                String data = jsonCardapioDia.getString("data");

                                JSONArray jsonArrayRefeicoesDia = jsonCardapioDia.getJSONArray("refeicoes");

                                ArrayList<ItemRefeicao> listaRefeicoes = new ArrayList<>();

                                for (int j = 0; j < jsonArrayRefeicoesDia.length(); j++) {
                                    JSONObject refeicao = jsonArrayRefeicoesDia.getJSONObject(j);
                                    double precoRefeicao = refeicao.getDouble("preco");
                                    String nomePrato = refeicao.getJSONObject("prato").getString("nome");
                                    String descricaoPrato = refeicao.getJSONObject("prato").getString("descricao");
                                    String tipoPrato = refeicao.getJSONObject("prato").getString("tipo");
                                    listaRefeicoes.add(new ItemRefeicao(precoRefeicao, new Prato(nomePrato, descricaoPrato, tipoPrato)));
                                }

                                JSONArray jsonArraySobremesasDia = jsonCardapioDia.getJSONArray("sobremesas");

                                ArrayList<ItemSobremesa> listaSobremesas = new ArrayList<>();

                                for (int j = 0; j < jsonArraySobremesasDia.length(); j++) {
                                    JSONObject refeicao = jsonArraySobremesasDia.getJSONObject(j);
                                    double precoSobremesa = refeicao.getDouble("preco");
                                    String nomePrato = refeicao.getJSONObject("prato").getString("nome");
                                    String descricaoPrato = refeicao.getJSONObject("prato").getString("descricao");
                                    String tipoPrato = refeicao.getJSONObject("prato").getString("tipo");
                                    listaSobremesas.add(new ItemSobremesa(precoSobremesa, new Prato(nomePrato, descricaoPrato, tipoPrato)));
                                }

                                cardapioAlmoco.add(new CardapioAlmoco(diaDaSemana, data, listaRefeicoes, listaSobremesas));

                            }

                            restaurante.setCardapioAlmoco(cardapioAlmoco);


                        }
                        if (!jsonObject.isNull("jantar")) {
                            String inicio = jsonObject.getJSONObject("jantar").getString("inicio");
                            String fim = jsonObject.getJSONObject("jantar").getString("fim");
                            restaurante.setHorarioJantar(new Horario(inicio, fim));


                            JSONArray jsonCardapioJantar = jsonRestaurante.getJSONObject("cardapio").getJSONArray("almoco");

                            ArrayList<CardapioJantar> cardapioJantar = new ArrayList<>();

                            for (int k = 0; k < jsonCardapioJantar.length(); k++) {

                                JSONObject jsonCardapioDia = jsonCardapioJantar.getJSONObject(k);
                                String diaDaSemana = jsonCardapioDia.getString("dia-da-semana");
                                String data = jsonCardapioDia.getString("data");

                                JSONArray jsonArrayRefeicoesDia = jsonCardapioDia.getJSONArray("refeicoes");

                                ArrayList<ItemRefeicao> listaRefeicoes = new ArrayList<>();

                                for (int j = 0; j < jsonArrayRefeicoesDia.length(); j++) {
                                    JSONObject refeicao = jsonArrayRefeicoesDia.getJSONObject(j);
                                    double precoRefeicao = refeicao.getDouble("preco");
                                    String nomePrato = refeicao.getJSONObject("prato").getString("nome");
                                    String descricaoPrato = refeicao.getJSONObject("prato").getString("descricao");
                                    String tipoPrato = refeicao.getJSONObject("prato").getString("tipo");
                                    listaRefeicoes.add(new ItemRefeicao(precoRefeicao, new Prato(nomePrato, descricaoPrato, tipoPrato)));
                                }

                                JSONArray jsonArraySobremesasDia = jsonCardapioDia.getJSONArray("sobremesas");

                                ArrayList<ItemSobremesa> listaSobremesas = new ArrayList<>();

                                for (int j = 0; j < jsonArraySobremesasDia.length(); j++) {
                                    JSONObject refeicao = jsonArraySobremesasDia.getJSONObject(j);
                                    double precoSobremesa = refeicao.getDouble("preco");
                                    String nomePrato = refeicao.getJSONObject("prato").getString("nome");
                                    String descricaoPrato = refeicao.getJSONObject("prato").getString("descricao");
                                    String tipoPrato = refeicao.getJSONObject("prato").getString("tipo");
                                    listaSobremesas.add(new ItemSobremesa(precoSobremesa, new Prato(nomePrato, descricaoPrato, tipoPrato)));
                                }

                                cardapioJantar.add(new CardapioJantar(diaDaSemana, data, listaRefeicoes, listaSobremesas));

                            }

                            restaurante.setCardapioJantar(cardapioJantar);

                        }
                        listaRestaurantes.add(restaurante);
                    }
                }else{
                    // server respondeu um erro;
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
            // Json vazio
            return null;
        }
        return listaRestaurantes;
    }

    @Override
    protected void onPostExecute(ArrayList<Restaurante> restaurantes) {
        super.onPostExecute(restaurantes);
    }
}
