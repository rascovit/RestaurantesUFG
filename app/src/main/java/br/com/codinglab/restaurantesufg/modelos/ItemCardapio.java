package br.com.codinglab.restaurantesufg.modelos;

/**
 * Created by PC MASTER RACE on 26/06/2015.
 */
public abstract class ItemCardapio {

    private double precoPrato;
    private Prato prato;

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


}
