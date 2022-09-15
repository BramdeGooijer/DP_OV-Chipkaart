package DAO.OVChipkaart;

import Domein.OVChipkaart;
import Domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OVChipkaartDSOpsql implements OVChipkaartDAO {
    private Connection connection;

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            String sqlQuery = "INSERT INTO ov_chipkaart VALUES(?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, ovChipkaart.getKaart_nummer());
            ps.setDate(2, ovChipkaart.getGeldig_tot());
            ps.setInt(3, ovChipkaart.getKlasse());
            ps.setInt(4, ovChipkaart.getSaldo());
            ps.setInt(5, ovChipkaart.getReiziger().getReiziger_id());

            ps.execute();
            ps.close();
            return true;

        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            String sqlQuery = "UPDATE ov_chipkaart SET geldig_tot=?, klasse=?, saldo=? WHERE kaart_nummer=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setDate(1, ovChipkaart.getGeldig_tot());
            ps.setInt(2, ovChipkaart.getKlasse());
            ps.setInt(3, ovChipkaart.getSaldo());
            ps.setInt(4, ovChipkaart.getKaart_nummer());

            ps.execute();
            ps.close();
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            String sqlQuery = "DELETE FROM ov_chipkaart WHERE kaart_nummer=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, ovChipkaart.getKaart_nummer());

            ps.execute();
            ps.close();
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() {
        return null;
    }
}
