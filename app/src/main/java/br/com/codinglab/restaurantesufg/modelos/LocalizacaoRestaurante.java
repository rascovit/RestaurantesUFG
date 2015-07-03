package br.com.codinglab.restaurantesufg.modelos;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public class LocalizacaoRestaurante extends Localizacao{

    private String campus;
    private String pontoDeReferencia;
    private String distanciaKM;
    private String tempViagem;
    private int campusId;

    public LocalizacaoRestaurante(String latitude, String longitude, String enderecoRestaurante, String campus, int campusId, String pontoDeReferencia) {
        super(latitude, longitude, enderecoRestaurante);
        this.campus = campus;
        this.pontoDeReferencia = pontoDeReferencia;
        this.distanciaKM = "Calculando...";
        this.tempViagem = "";
        this.campusId = campusId;

    }

    public void setDistancia(String distanceTexto) {
        this.distanciaKM = distanceTexto;
    }

    public void setTempViagem(String tempoViagemTexto) {
        this.tempViagem = tempoViagemTexto;
    }

    public String getEnderecoRestaurante() {
        return getEndereco();
    }

    public String getCampus() {
        return campus;
    }

    public String getPontoDeReferencia() {
        return pontoDeReferencia;
    }

    public String getDistancia() {
        return distanciaKM;
    }

    public String getTempoViagem() {
        return tempViagem;
    }

    public int getCampusId() {
        return campusId;
    }
}
