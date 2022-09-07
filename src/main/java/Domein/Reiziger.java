package Domein;

import java.sql.Date;

public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    @Override
    public String toString() {
        return String.format("ID: %s\n" +
                "Voorletters: %s\n" +
                "Tussenvoegsel: %s\n" +
                "Achternaam: %s\n" +
                "Geboortedatum: %s"
                , this.reiziger_id, this.voorletters, this.tussenvoegsel, this.achternaam, this.geboortedatum);
    }
}