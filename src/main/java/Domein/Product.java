package Domein;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private int prijs;

//    maak dit list integer naar ovchipkaart
    private List<Integer> alleOVChipkaarten = new ArrayList<>();

    public Product(int product_nummer, String naam, String beschrijving, int prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public List<Integer> getAlleOVChipkaarten() {
        return alleOVChipkaarten;
    }

    public void addOVChipkaart(int ovChipkaartNummer) {
        alleOVChipkaarten.add(ovChipkaartNummer);
    }

    public void removeOVChipkaart(int ovChipkaartNummer) {
        alleOVChipkaarten.remove(ovChipkaartNummer);
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public int getPrijs() {
        return prijs;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", this.product_nummer, this.naam, this.beschrijving, this.prijs, this.alleOVChipkaarten);
    }
}
