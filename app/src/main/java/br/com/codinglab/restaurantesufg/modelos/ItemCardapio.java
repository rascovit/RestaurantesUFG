package br.com.codinglab.restaurantesufg.modelos;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public abstract class ItemCardapio {

    private double precoPrato;
    private Prato prato;
    private String servidoDia;
    private String servidoNo; //café da manhã ou almoço ou jantar

    public ItemCardapio(double precoPrato, Prato prato) {
        this.precoPrato = precoPrato;
        this.prato = prato;
    }

    public double getPrecoPrato() {
        return precoPrato;
    }

    public Prato getPrato(){
        return prato;
    }

    public String getServidoDia() {
        return servidoDia;
    }

    public void setServidoDia(String servidoDia) {
        this.servidoDia = servidoDia;
    }

    public String getServidoNo() {
        return servidoNo;
    }

    public void setServidoNo(String servidoNo) {
        this.servidoNo = servidoNo;
    }
}
