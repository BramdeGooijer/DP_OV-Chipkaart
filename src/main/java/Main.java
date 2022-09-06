import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql:ovchip";
        String username = "postgres";
        String password = "HBOICTBram";

        Connection db = DriverManager.getConnection(url, username, password);

        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM reiziger");
        while (rs.next())
        {
            System.out.println(String.format("Id: %s, Voorletters: %s, Tussenvoegsel: %s, Achternaam: %s, Geboortedatum: %s", rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        rs.close();
        st.close();


    }
}
