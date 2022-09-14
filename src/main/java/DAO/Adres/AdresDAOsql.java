package DAO.Adres;

import DAO.Reiziger.ReizigerDAO;
import Domein.Adres;
import Domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOsql implements AdresDAO{
    private Connection connection;
    private ReizigerDAO rdao;

    public AdresDAOsql(Connection connection) {
        this.connection = connection;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            String sqlQuery = "INSERT INTO adres VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, adres.getAdres_id());
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5, adres.getWoonplaats());
            ps.setInt(6, adres.getReiziger().getReiziger_id());

            ps.execute();
            ps.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            String sqlQuery = "UPDATE adres SET postcode=?, huisnummer=?, straat=?, woonplaats=? WHERE adres_id=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setString(1, adres.getPostcode());
            ps.setString(2, adres.getHuisnummer());
            ps.setString(3, adres.getStraat());
            ps.setString(4, adres.getStraat());
            ps.setInt(5, adres.getAdres_id());

            ps.execute();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println("The program failed to update the adres!\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String sqlQuery = "DELETE FROM adres WHERE adres_id=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, adres.getAdres_id());

            ps.execute();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println("The program failed to delete the adres!\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String sqlQuery = "SELECT * FROM adres WHERE reiziger_id=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, reiziger.getReiziger_id());

            ResultSet rs = ps.executeQuery();
            rs.next();
//            de reiziger werd ten onrechte uit de database gehaald en dat had ik al in een eerdere commit gefixt hetzelfde geld bij findAll
            return new Adres(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), reiziger);

        } catch (SQLException e) {
            if(e.getMessage().equals("ResultSet not positioned properly, perhaps you need to call next.")) {
                return null;
            }
            System.out.println("Couldn't find Adres!\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        try {
            String sqlQuery = "SELECT * FROM adres";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);

            ResultSet rs = ps.executeQuery();
            List<Adres> alleAdressen = new ArrayList<>();

            while (rs.next()) {
                // findAll haalt nu ook de reiziger op bij het adres, hij is toegevoegd aan het einde van de line hieronder
                alleAdressen.add(new Adres(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rdao.findById(rs.getInt(6))));
            }

            return alleAdressen;
        } catch (SQLException e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return null;
        }
    }
}
