package Domein;

import java.sql.Date;

public class OVChipkaart {
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private int saldo;
    private Reiziger reiziger;

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, int saldo, Reiziger reiziger) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        reiziger.addOVChipkaart(this);
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
