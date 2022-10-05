package Domein;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private int prijs;
    private List<ov_chipkaart_product> alle_ov_chipkaart_producten = new ArrayList<>();

    public Product(int product_nummer, String naam, String beschrijving, int prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public void add_ov_chipkaart_product(ov_chipkaart_product product) {
        alle_ov_chipkaart_producten.add(product);
    }

    public void delete_ov_chipkaart_product(ov_chipkaart_product product) {
        alle_ov_chipkaart_producten.remove(product);
    }

    public List<ov_chipkaart_product> getAlleOVProducten() {
        return alle_ov_chipkaart_producten;
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
}
