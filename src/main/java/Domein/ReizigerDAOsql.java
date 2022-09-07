package Domein;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ReizigerDAOsql implements ReizigerDAO{
    private Connection connection;

    public ReizigerDAOsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            String sqlQuery = "INSERT INTO reiziger VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, reiziger.getReiziger_id());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setDate(5, reiziger.getGeboortedatum());

            ResultSet rs = ps.executeQuery();
            rs.close();
            ps.close();
            return true;
        } catch (SQLException e) {
            if (Objects.equals(e.getMessage(), "Geen resultaten werden teruggegeven door de query.")) {
                return true;
            } else {
                System.out.println("The program failed to save the reiziger!\n" + e.getMessage());
                return false;
            }
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            String sqlQuery = "UPDATE reiziger SET voorletters=?, tussenvoegsel=?, achternaam=?, geboortedatum=? WHERE reiziger_id=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(5, reiziger.getReiziger_id());
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, reiziger.getGeboortedatum());

            ResultSet rs = ps.executeQuery();
            rs.close();
            ps.close();
            return true;
        } catch (SQLException e) {
            if (Objects.equals(e.getMessage(), "Geen resultaten werden teruggegeven door de query.")) {
                return true;
            } else {
                System.out.println("The program failed to save the reiziger!\n" + e.getMessage());
                return false;
            }
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        return null;
    }

    @Override
    public List<Reiziger> findByGbDatum(String datum) {
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        return null;
    }
}
