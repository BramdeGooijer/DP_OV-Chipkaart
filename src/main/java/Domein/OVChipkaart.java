package Domein;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private int saldo;
    private Reiziger reiziger;
    private List<ov_chipkaart_product> alle_ov_chipkaart_producten = new ArrayList<>();

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, int saldo, Reiziger reiziger) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        reiziger.addOVChipkaart(this);
    }

    public void add_ov_chipkaart_product(ov_chipkaart_product product) {
        alle_ov_chipkaart_producten.add(product);
    }

    public void delete_ov_chipkaart_product(ov_chipkaart_product product) {
        alle_ov_chipkaart_producten.remove(product);
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public int getSaldo() {
        return saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public String toString() {
        return String.format("OVChipkaart {%s, %s, %s, %s}", this.kaart_nummer, this.geldig_tot, this.klasse, this.saldo);
    }
}
