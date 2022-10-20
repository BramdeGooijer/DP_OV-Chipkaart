package DAO.OVChipkaart;

import DAO.Product.ProductDAO;
import DAO.Reiziger.ReizigerDAO;
import Domein.OVChipkaart;
import Domein.Product;
import Domein.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOpsql implements OVChipkaartDAO {
    private Connection connection;
    private ReizigerDAO rdao;
    private ProductDAO pdao;

    public OVChipkaartDAOpsql(Connection connection) {
        this.connection = connection;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    public void setPdao(ProductDAO pdao) {
        this.pdao = pdao;
    }

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

//            dit in product maar loop wel door producten heen om te saven
            for (Product perProduct : ovChipkaart.getAlleProducten()) {
                pdao.save(perProduct);
            }

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

            for (Product perProduct : ovChipkaart.getAlleProducten()) {
                pdao.update(perProduct);
            }

//            for (Product perProduct : ovChipkaart.getAlleProducten()) {
//                String sqlQuery2 = "update ov_chipkaart_product set status=?, last_update=? where kaart_nummer=?";
//                PreparedStatement ps2 = connection.prepareStatement(sqlQuery2);
//                ps2.setString(1, "negative");
//                ps2.setDate(2, Date.valueOf("2001-01-01"));
//                ps2.setInt(3, ovChipkaart.getKaart_nummer());
//
//                ps2.execute();
//                ps2.close();
//            }


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

            for (Product perProduct : ovChipkaart.getAlleProducten()) {
                pdao.delete(perProduct);
            }

//            for(Product perProduct : ovChipkaart.getAlleProducten()) {
//                String sqlQuery2 = "delete from ov_chipkaart_product where kaart_nummer=?";
//                PreparedStatement ps2 = connection.prepareStatement(sqlQuery);
//                ps2.setInt(1, ovChipkaart.getKaart_nummer());
//
//                ps2.execute();
//                ps2.close();
//            }

            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            String sqlQuery = "SELECT * FROM ov_chipkaart WHERE reiziger_id=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, reiziger.getReiziger_id());

            ResultSet rs = ps.executeQuery();
            List<OVChipkaart> alleOVChipkaarten = new ArrayList<>();
            while (rs.next()) {
                OVChipkaart ovChipkaart = new OVChipkaart(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), reiziger);

                for (Product perProduct : pdao.findByOVChipkaart(ovChipkaart)) {
                    ovChipkaart.addProduct(perProduct);
                }

                alleOVChipkaarten.add(ovChipkaart);
            }

            return alleOVChipkaarten;

        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        try {
            String sqlQuery = "SELECT * FROM ov_chipkaart";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);

            ResultSet rs = ps.executeQuery();
            List<OVChipkaart> alleOVChipkaarten = new ArrayList<>();

            while (rs.next()) {
                OVChipkaart ovChipkaart = new OVChipkaart(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), rdao.findById(rs.getInt(5)));

                for (Product perProduct : pdao.findByOVChipkaart(ovChipkaart)) {
                    ovChipkaart.addProduct(perProduct);
                }

                alleOVChipkaarten.add(ovChipkaart);
            }

            return alleOVChipkaarten;
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return null;
        }
    }
}
