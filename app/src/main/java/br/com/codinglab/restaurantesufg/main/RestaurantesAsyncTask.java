package br.com.codinglab.restaurantesufg.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import br.com.codinglab.restaurantesufg.utils.Handler;

/**
 * Created by PC MASTER RACE on 28/06/2015.
 */
public class RestaurantesAsyncTask extends AsyncTask<String, Void, ArrayList<Restaurante>> {

    private Context contexto;
    private ProgressDialog progressDialog;

    public RestaurantesAsyncTask(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(contexto);
        progressDialog.setTitle("Informações");
        progressDialog.setMessage("Carregando informações...");
        progressDialog.show();
    }

    @Override
    protected ArrayList<Restaurante> doInBackground(String... params) {

        String campus = params[0];
        String url = "http://codinglab.com.br/samuel/restaurantes.php?campus=" + campus;
        String jsonResposta = "";
        ArrayList<Restaurante> listaRestaurantes = new ArrayList<>();
        Handler handler = new Handler();
        jsonResposta = handler.makeServiceCall(url, Handler.GET);

        if (jsonResposta != "") {

            try {
                jsonResposta = "{ \"status\" : \"OK\", \"restaurantes\" : [ { \"id\" : 101022, \"nome\" : \"Estação Reune\", \"valor-minimo\" : 14.99, \"estilo-de-servir\" : \"Por Kilo\", \"localizacao\" : { \"endereco\" : \"Alameda Palmeiras\", \"campus\" : \"Campus II - Samanbaia\", \"ponto-de-referencia\" : \"Atrás do Centro de Aulas Baru\", \"latitude\" : -16.603447, \"longitude\" : -49.266078 }, \"horarios\" : { \"cafe-da-manha\" : { \"inicio\" : \"06:30\", \"fim\" : \"06:30\" }, \"almoco\" : { \"inicio\" : \"11:00\", \"fim\" : \"15:00\" }, \"jantar\" : { \"inicio\" : \"18:00\", \"fim\" : \"23:00\" } }, \"cardapio\" : { \"cafe-da-manha\" : [ { \"dia-da-semana\" : \"Segunda-Feira\", \"data\" : \"29/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ] }, { \"dia-da-semana\" : \"Terça-Feira\", \"data\" : \"30/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ] } ], \"almoco\" : [ { \"dia-da-semana\" : \"Segunda-Feira\", \"data\" : \"29/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ], \"sobremesas\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Sobremesa 1\", \"descricao\" : \"Descrição sobremesa 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Sobremesa 2\", \"descricao\" : \"Descrição sobremesa 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Sobremesa 3\", \"descricao\" : \"Descrição sobremesa 3\", \"tipo\" : \"Vegetariano\" } } ] }, { \"dia-da-semana\" : \"Terça-Feira\", \"data\" : \"30/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ], \"sobremesas\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Sobremesa 1\", \"descricao\" : \"Descrição sobremesa 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Sobremesa 2\", \"descricao\" : \"Descrição sobremesa 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Sobremesa 3\", \"descricao\" : \"Descrição sobremesa 3\", \"tipo\" : \"Vegetariano\" } } ] } ], \"jantar\" : [ { \"dia-da-semana\" : \"Segunda-Feira\", \"data\" : \"29/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ], \"sobremesas\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Sobremesa 1\", \"descricao\" : \"Descrição sobremesa 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Sobremesa 2\", \"descricao\" : \"Descrição sobremesa 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Sobremesa 3\", \"descricao\" : \"Descrição sobremesa 3\", \"tipo\" : \"Vegetariano\" } } ] }, { \"dia-da-semana\" : \"Terça-Feira\", \"data\" : \"30/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ], \"sobremesas\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Sobremesa 1\", \"descricao\" : \"Descrição sobremesa 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Sobremesa 2\", \"descricao\" : \"Descrição sobremesa 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Sobremesa 3\", \"descricao\" : \"Descrição sobremesa 3\", \"tipo\" : \"Vegetariano\" } } ] } ] } }, { \"id\" : 125547, \"nome\" : \"Restaurante Universitário\", \"valor-minimo\" : 3.50, \"estilo-de-servir\" : \"A la carte\", \"localizacao\" : { \"endereco\" : \"Alameda Palmeiras\", \"campus\" : \"Campus II - Samanbaia\", \"ponto-de-referencia\" : \"Próximo a agência da Caixa Econômica.\", \"latitude\" : -16.601942, \"longitude\" : -49.261731 }, \"horarios\" : { \"cafe-da-manha\" : { \"inicio\" : \"06:30\", \"fim\" : \"06:30\" }, \"almoco\" : { \"inicio\" : \"11:00\", \"fim\" : \"15:00\" }, \"jantar\" : { \"inicio\" : \"18:00\", \"fim\" : \"23:00\" } }, \"cardapio\" : { \"cafe-da-manha\" : [ { \"dia-da-semana\" : \"Segunda-Feira\", \"data\" : \"29/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ] }, { \"dia-da-semana\" : \"Terça-Feira\", \"data\" : \"30/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ] } ], \"almoco\" : [ { \"dia-da-semana\" : \"Segunda-Feira\", \"data\" : \"29/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ], \"sobremesas\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Sobremesa 1\", \"descricao\" : \"Descrição sobremesa 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Sobremesa 2\", \"descricao\" : \"Descrição sobremesa 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Sobremesa 3\", \"descricao\" : \"Descrição sobremesa 3\", \"tipo\" : \"Vegetariano\" } } ] }, { \"dia-da-semana\" : \"Terça-Feira\", \"data\" : \"30/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ], \"sobremesas\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Sobremesa 1\", \"descricao\" : \"Descrição sobremesa 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Sobremesa 2\", \"descricao\" : \"Descrição sobremesa 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Sobremesa 3\", \"descricao\" : \"Descrição sobremesa 3\", \"tipo\" : \"Vegetariano\" } } ] } ], \"jantar\" : [ { \"dia-da-semana\" : \"Segunda-Feira\", \"data\" : \"29/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ], \"sobremesas\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Sobremesa 1\", \"descricao\" : \"Descrição sobremesa 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Sobremesa 2\", \"descricao\" : \"Descrição sobremesa 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Sobremesa 3\", \"descricao\" : \"Descrição sobremesa 3\", \"tipo\" : \"Vegetariano\" } } ] }, { \"dia-da-semana\" : \"Terça-Feira\", \"data\" : \"30/06/2015\", \"refeicoes\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Prato 1\", \"descricao\" : \"Descrição Prato 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Prato 2\", \"descricao\" : \"Descrição Prato 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Prato 3\", \"descricao\" : \"Descrição Prato 3\", \"tipo\" : \"Vegetariano\" } } ], \"sobremesas\" : [ { \"preco\" : 5.00, \"prato\" : { \"nome\" : \"Sobremesa 1\", \"descricao\" : \"Descrição sobremesa 1\", \"tipo\" : \"Comum\" } }, { \"preco\" : 3.00, \"prato\" : { \"nome\" : \"Sobremesa 2\", \"descricao\" : \"Descrição sobremesa 2\", \"tipo\" : \"Comum\" } }, { \"preco\" : 4.00, \"prato\" : { \"nome\" : \"Sobremesa 3\", \"descricao\" : \"Descrição sobremesa 3\", \"tipo\" : \"Vegetariano\" } } ] } ] } } ] }";
                JSONObject jsonPrincipal = new JSONObject(jsonResposta);
                String serverStatus = jsonPrincipal.getString("status");
                if (serverStatus.equals("OK")) {

                    JSONArray jsonArrayRestaurantes = jsonPrincipal.getJSONArray("restaurantes");

                    for (int i = 0; i < jsonArrayRestaurantes.length(); i++) {
                        JSONObject jsonRestaurante = jsonArrayRestaurantes.getJSONObject(i);

                        int idRestaurante = jsonRestaurante.getInt("id");
                        String nomeRestaurante = jsonRestaurante.getString("nome");
                        double valorMinimo = jsonRestaurante.getDouble("valor-minimo");
                        String estiloDeServir = jsonRestaurante.getString("estilo-de-servir");
                        JSONObject jsonObject = jsonRestaurante.getJSONObject("localizacao");
                        String endereco = jsonObject.getString("endereco");
                        String campusRestaurante = jsonObject.getString("campus");
                        String pontoReferencia = jsonObject.getString("ponto-de-referencia");
                        String latitude = String.valueOf(jsonObject.getString("latitude"));
                        String longitude = String.valueOf(jsonObject.getString("longitude"));

                        LocalizacaoRestaurante localizacaoRestaurante = new LocalizacaoRestaurante(latitude, longitude, endereco, campusRestaurante, pontoReferencia);
                        Restaurante restaurante = new Restaurante(idRestaurante, nomeRestaurante, valorMinimo, estiloDeServir, localizacaoRestaurante);

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
                } else {
                    // server respondeu; um erro
                    Log.e("STATUS ERRADO", String.valueOf(listaRestaurantes.size()));
                    return listaRestaurantes;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("JSON VAZIO", String.valueOf(listaRestaurantes.size()));
            return listaRestaurantes;
        }
        Log.e("RETORNO PADRÃO", String.valueOf(listaRestaurantes.size()));
        return listaRestaurantes;
    }

    @Override
    protected void onPostExecute(ArrayList<Restaurante> restaurantes) {
        progressDialog.dismiss();
    }
}
