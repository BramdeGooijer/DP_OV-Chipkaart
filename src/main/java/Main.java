import DAO.Adres.AdresDAO;
import DAO.Adres.AdresDAOsql;
import DAO.OVChipkaart.OVChipkaartDAO;
import DAO.OVChipkaart.OVChipkaartDAOpsql;
import Domein.Adres;
import Domein.OVChipkaart;
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
            OVChipkaartDAOpsql ovChipkaartDAOpsql = new OVChipkaartDAOpsql(db);

            reizigerDAOsql.setAdao(adresDAOsql);
            reizigerDAOsql.setOdao(ovChipkaartDAOpsql);
            adresDAOsql.setRdao(reizigerDAOsql);
            ovChipkaartDAOpsql.setRdao(reizigerDAOsql);

            // Hier staan de tests voor reiziger en adres
//            testReizigerDAO(reizigerDAOsql);
//            testAdresDAO(adresDAOsql, reizigerDAOsql);
            testOVChipkaart(ovChipkaartDAOpsql, reizigerDAOsql);

            db.close();

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
        Reiziger sietske = new Reiziger(100, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Update een bestaande reiziger en persisteer deze in de database
        Reiziger updatedReiziger = new Reiziger(101, "Henk", "de", "Gooijer", Date.valueOf("2004-01-26"));
        rdao.save(updatedReiziger);
        updatedReiziger = new Reiziger(101, "Bram", "de", "Gooijer", Date.valueOf("2004-01-26"));
        System.out.println(String.format("[Test] Eerst is de Reiziger:\n %s\n -----En na het updaten is het-----", rdao.findById(101)));
        rdao.update(updatedReiziger);
        System.out.println(rdao.findById(101));

        //Verwijder een reiziger uit de db
        Reiziger deletedReiziger = new Reiziger(102, "Jeroen", null, "Fredrikzs", Date.valueOf("2000-01-01"));
        rdao.save(deletedReiziger);
        System.out.println(String.format("\n[Test] Eerst is de lijst met alle reizigers:\n%s", rdao.findAll()));
        rdao.delete(deletedReiziger);
        System.out.println(String.format("[Test] Daarna ziet de lijst er zo uit:\n%s", rdao.findAll()));

        // Find by geboortedatum
        System.out.println(String.format("[Test] Hier alle reizigers met de geboortedatum 2002-12-03:\n%s", rdao.findByGbDatum("2002-12-03")));
        // Einde P2
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        // een reiziger die we gebruiken met een adres
        Reiziger mijnReiziger2 = new Reiziger(103, "jantje", null, "Piet", Date.valueOf("2002-01-26"));
        rdao.save(mijnReiziger2);
        // het adres die bij de reiziger hoort
        Adres mijnAdres = new Adres(200, "1218", "5", "juliusnogwatteslaan", "HIllie", mijnReiziger2);

        // test save adres
        System.out.println("[Test] save adres:");
        adao.save(mijnAdres);
        System.out.println(adao.findByReiziger(mijnReiziger2));

        // test findAll
        System.out.println("[Test] findAll:");
        System.out.println(adao.findAll());

        // test find by reiziger
        System.out.println("[Test] find by reiziger:");
        System.out.println(adao.findByReiziger(mijnReiziger2));

        // test update adres
        Reiziger mijnReiziger3 = new Reiziger(104, "piet", "de", "zeeuw", Date.valueOf("2002-01-27"));
        rdao.save(mijnReiziger3);
        Adres mijnAdres3 = new Adres(201, "9999", "420", "welislaan", "Adam", mijnReiziger3);
        adao.save(mijnAdres3);

        System.out.println("[Test] update Adres");
        System.out.println(String.format("[Test] eerst is het adres:\n%s", adao.findByReiziger(mijnReiziger3)));

        mijnAdres3 = new Adres(201, "1111", "1", "brediusweg", "zaandam", mijnReiziger3);
        System.out.println(adao.update(mijnAdres3));
        System.out.println(String.format("[Test] nu is het adres:\n%s", adao.findByReiziger(mijnReiziger3)));

        // test delete adres
        System.out.println("[Test delete Adres]");
        System.out.println(String.format("[Test] Eerst vind hij het adres wel:\n%s", adao.findByReiziger(mijnReiziger3)));
        System.out.println(adao.delete(mijnAdres3));
        System.out.println(String.format("[Test] daarna is het adres verwijderd:\n%s", adao.findByReiziger(mijnReiziger3)));
    }

    public static void testOVChipkaart(OVChipkaartDAO odao, ReizigerDAO rdao) {
        // Test de save functie
        // Save
        Reiziger testReiziger1 = new Reiziger(300, "K", "van", "Karel", Date.valueOf("2000-01-01"));
        System.out.println("    [INFO]      Reiziger wordt opgeslagen");
        rdao.save(testReiziger1);
        System.out.println(rdao.findById(300));

        OVChipkaart testOVChipkaart1 = new OVChipkaart(300, Date.valueOf("2025-01-01"), 1, 500, testReiziger1);
        System.out.println("    [INFO]      OVChipkaart wordt opgeslagen");
        odao.save(testOVChipkaart1);
        System.out.println(odao.findByReiziger(testReiziger1));

        // Update
        System.out.println("    [INFO]      Eerst ziet de ovchipkaart er zo uit:");
        System.out.println(odao.findByReiziger(testReiziger1));
        testOVChipkaart1 = new OVChipkaart(300, Date.valueOf("2025-01-01"), 2, 500, testReiziger1);
        System.out.println("    [INFO]      OVChipkaart wordt geupdate");
        odao.update(testOVChipkaart1);
        System.out.println("    [INFO]      En na het updaten zo:");
        System.out.println(odao.findByReiziger(testReiziger1));

        // Delete
        OVChipkaart testOVChipkaart2 = new OVChipkaart(301, Date.valueOf("2030-01-01"), 1, 200, testReiziger1);
        System.out.println("    [INFO]      OVChipkaart wordt opgeslagen");
        odao.save(testOVChipkaart2);
        System.out.println("    [INFO]      Eerst ziet de lijst met alle ovchipkaarten van de rijziger er zo uit:");
        System.out.println(odao.findByReiziger(testReiziger1));
        System.out.println("    [INFO]      OVChipkaart wordt verwijderd");
        odao.delete(testOVChipkaart2);
        System.out.println("    [INFO]      Na het verwijderen ziet de lijst er zo uit:");
        System.out.println(odao.findByReiziger(testReiziger1));

        // findAll()
        System.out.println("    [INFO]      Dit is de lijst van alle ovchipkaarten:");
        System.out.println(odao.findAll());

        // ovchipkaarten in de toString van reiziger
        Reiziger testReiziger2 = new Reiziger(301, "B", "van", "Doorn", Date.valueOf("2000-01-01"));
        System.out.println("    [INFO]      Reiziger wordt opgeslagen");
        rdao.save(testReiziger2);
        System.out.println(rdao.findById(301));

        OVChipkaart testOVChipkaart3 = new OVChipkaart(302, Date.valueOf("2025-01-01"), 1, 10, testReiziger2);
        System.out.println("    [INFO]      OVChipkaart wordt opgeslagen");
        odao.save(testOVChipkaart3);
        OVChipkaart testOVChipkaart4 = new OVChipkaart(303, Date.valueOf("2025-01-01"), 2, 200, testReiziger2);
        System.out.println("    [INFO]      OVChipkaart wordt opgeslagen");
        odao.save(testOVChipkaart4);
        OVChipkaart testOVChipkaart5 = new OVChipkaart(304, Date.valueOf("2025-01-01"), 3, 500, testReiziger2);
        System.out.println("    [INFO]      OVChipkaart wordt opgeslagen");
        odao.save(testOVChipkaart5);

        System.out.println("    [INFO]      OVChipkaarten getoond via de toString van reiziger:");
        System.out.println(rdao.findById(301));
    }
}
