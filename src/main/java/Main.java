import Domein.Reiziger;
import Domein.ReizigerDAOsql;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql:ovchip";
        String username = "postgres";
        String password = "HBOICTBram";

        Connection db = DriverManager.getConnection(url, username, password);

        ReizigerDAOsql reizigerDAOsql = new ReizigerDAOsql(db);
        Reiziger mijnReiziger = new Reiziger(6, "bbb", "de", "Gggg", Date.valueOf("1970-07-13"));

//        reizigerDAOsql.save(mijnReiziger);
//        reizigerDAOsql.update(mijnReiziger);
//        reizigerDAOsql.delete(mijnReiziger);
        System.out.println(reizigerDAOsql.findById(6));;

//        Statement st = db.createStatement();
//        ResultSet rs = st.executeQuery("SELECT * FROM reiziger");
//        while (rs.next())
//        {
//            System.out.println(String.format("Id: %s, Voorletters: %s, Tussenvoegsel: %s, Achternaam: %s, Geboortedatum: %s", rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5)));
//        }
//        rs.close();
//        st.close();

//        Reiziger mijnReiziger = new Reiziger(1, "B.R.", "de", "Gooijer", Date.valueOf("2004-01-26"));
//        System.out.println(mijnReiziger);
    }
}
