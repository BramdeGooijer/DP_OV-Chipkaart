import Domein.Reiziger;
import Interface.ReizigerDAOsql;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql:ovchip";
        String username = "postgres";
        String password = "HBOICTBram";

        try {
            Connection db = DriverManager.getConnection(url, username, password);

            ReizigerDAOsql reizigerDAOsql = new ReizigerDAOsql(db);
            Reiziger mijnReiziger = new Reiziger(51, "Appie", null, "Elcik", Date.valueOf("2002-01-26"));

//            reizigerDAOsql.save(mijnReiziger);
//            reizigerDAOsql.update(mijnReiziger);
//            reizigerDAOsql.delete(mijnReiziger);
//            System.out.println(reizigerDAOsql.findById(50));
//            System.out.println(reizigerDAOsql.findByGbDatum("2002-12-03"));
//            System.out.println(reizigerDAOsql.findAll());

        } catch (Exception e) {
            System.out.println("Something went wrong!");
            System.out.println(e.getMessage());
        }
    }
}
