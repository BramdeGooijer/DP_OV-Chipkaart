import DAO.Adres.AdresDAO;
import DAO.Adres.AdresDAOsql;
import Domein.Adres;
import Domein.Reiziger;
import DAO.Reiziger.ReizigerDAO;
import DAO.Reiziger.ReizigerDAOsql;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql:ovchip";
        String username = "postgres";
        String password = "HBOICTBram";

        try {
            Connection db = DriverManager.getConnection(url, username, password);

            ReizigerDAOsql reizigerDAOsql = new ReizigerDAOsql(db);
            AdresDAOsql adresDAOsql = new AdresDAOsql(db);

            reizigerDAOsql.setAdao(adresDAOsql);
            adresDAOsql.setRdao(reizigerDAOsql);

//            Reiziger mijnReiziger = new Reiziger(51, "Appie", null, "Elcik", Date.valueOf("2002-01-26"));
//            reizigerDAOsql.save(mijnReiziger);
//            reizigerDAOsql.update(mijnReiziger);
//            reizigerDAOsql.delete(mijnReiziger);
//            System.out.println(reizigerDAOsql.findById(50));
//            System.out.println(reizigerDAOsql.findByGbDatum("2002-12-03"));
//            System.out.println(reizigerDAOsql.findAll());

//            testReizigerDAO(reizigerDAOsql);


            testAdresDAO(adresDAOsql, reizigerDAOsql);

        } catch (Exception e) {
            System.out.println("Something went wrong!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Update een bestaande reiziger en persisteer deze in de database
        Reiziger updatedReiziger = new Reiziger(50, "Bram", "de", "Gooijer", Date.valueOf("2004-01-26"));
        System.out.println(String.format("[Test] Eerst is de Reiziger:\n %s\n -----En na het updaten is het-----", rdao.findById(50)));
        rdao.update(updatedReiziger);
        System.out.println(rdao.findById(50));

        //Verwijder een reiziger uit de db
        Reiziger deletedReiziger = new Reiziger(52, "Jeroen", null, "Fredrikzs", Date.valueOf("2000-01-01"));
        rdao.save(deletedReiziger);
        System.out.println(String.format("\n[Test] Eerst is de lijst met alle reizigers:\n%s", rdao.findAll()));
        rdao.delete(deletedReiziger);
        System.out.println(String.format("[Test] Daarna ziet de lijst er zo uit:%s", rdao.findAll()));

        // Find by geboortedatum
        System.out.println(String.format("[Test] Hier alle reizigers met de geboortedatum 2002-12-03:\n%s", rdao.findByGbDatum("2002-12-03")));
        // Einde P2
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        // een reiziger die we gebruiken met een adres
        Reiziger mijnReiziger2 = new Reiziger(55, "jantje", null, "Piet", Date.valueOf("2002-01-26"));
        // het adres die bij de reiziger hoort
        Adres mijnAdres = new Adres(50, "1218", "5", "juliusnogwatteslaan", "HIllie", mijnReiziger2);

        // test save adres
        System.out.println("[Test] save adres:");
        rdao.save(mijnReiziger2);
        adao.save(mijnAdres);

        // test findAll
        System.out.println("[Test] findAll:");
        System.out.println(adao.findAll());

        // test find by reiziger
        System.out.println("[Test] find by reiziger:");
        System.out.println(adao.findByReiziger(mijnReiziger2));

        // test update adres
        Reiziger mijnReiziger3 = new Reiziger(56, "piet", "de", "zeeuw", Date.valueOf("2002-01-27"));
        Adres mijnAdres3 = new Adres(51, "9999", "420", "welislaan", "Adam", mijnReiziger3);
        rdao.save(mijnReiziger3);
        adao.save(mijnAdres3);

        System.out.println("[Test] update Adres");
        System.out.println(String.format("[Test] eerst is het adres:\n%s", adao.findByReiziger(mijnReiziger3)));

        mijnAdres3 = new Adres(51, "1111", "1", "brediusweg", "zaandam", mijnReiziger3);
        System.out.println(adao.update(mijnAdres3));
        System.out.println(String.format("[Test] nu is het adres:\n%s", adao.findByReiziger(mijnReiziger3)));

        // test delete adres
        System.out.println("[Test delete Adres]");
        System.out.println(String.format("[Test] Eerst vind hij het adres wel:\n%s", adao.findByReiziger(mijnReiziger3)));
        System.out.println(adao.delete(mijnAdres3));
        System.out.println(String.format("[Test] daarna is het adres verwijderd:\n%s", adao.findByReiziger(mijnReiziger3)));
    }
}
