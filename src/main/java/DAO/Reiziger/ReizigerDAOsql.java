package DAO.Reiziger;

import DAO.Adres.AdresDAO;
import Domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOsql implements ReizigerDAO{
    private Connection connection;
    AdresDAO adao;

    public ReizigerDAOsql(Connection connection) {
        this.connection = connection;
    }

    public void setAdao(AdresDAO adao) {
        this.adao = adao;
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

            if (reiziger.getAdres() != null) {
                adao.save(reiziger.getAdres());
            }

            ps.execute();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println("The program failed to save the reiziger!\n" + e.getMessage());
            return false;
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

            if (reiziger.getAdres() != null) {
                adao.update(reiziger.getAdres());
            }

            ps.execute();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println("The program failed to update the reiziger!\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String sqlQuery = "DELETE FROM reiziger WHERE reiziger_id=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, reiziger.getReiziger_id());

            if (reiziger.getAdres() != null) {
                adao.delete(reiziger.getAdres());
            }

            ps.execute();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println("The program failed to delete the reiziger!\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            String sqlQuery = "SELECT * FROM reiziger WHERE reiziger_id=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reiziger deReiziger = new Reiziger(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getDate(5));
                deReiziger.setAdres(adao.findByReiziger(deReiziger));
                return deReiziger;
            }

            throw new SQLException("Reiziger does not exist");
        } catch (SQLException e) {
            System.out.println("The reiziger hasn't been found!\n" + e.getMessage());;
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbDatum(String datum) {
        try {
            String sqlQuery = "SELECT * FROM reiziger WHERE geboortedatum=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setDate(1, Date.valueOf(datum));

            ResultSet rs = ps.executeQuery();
            List<Reiziger> alleReizigers = new ArrayList<>();

            while (rs.next()) {
                Reiziger deReiziger = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                deReiziger.setAdres(adao.findByReiziger(deReiziger));
                alleReizigers.add(deReiziger);
            }

            return alleReizigers;
        } catch (SQLException e) {
            System.out.println("The reiziger hasn't been found!\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            String sqlQuery = "SELECT * FROM reiziger";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);

            ResultSet rs = ps.executeQuery();
            List<Reiziger> alleReizigers = new ArrayList<>();

            while (rs.next()) {
                Reiziger deReiziger = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                deReiziger.setAdres(adao.findByReiziger(deReiziger));

                alleReizigers.add(deReiziger);
            }

            return alleReizigers;
        } catch (SQLException e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return null;
        }
    }
}
