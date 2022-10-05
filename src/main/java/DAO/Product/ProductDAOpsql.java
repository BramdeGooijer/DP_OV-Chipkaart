package DAO.Product;

import DAO.OVChipkaart.OVChipkaartDAO;
import Domein.Product;
import Domein.ov_chipkaart_product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAOpsql implements ProductDAO{
    private Connection connection;
    private OVChipkaartDAO odao;

    public ProductDAOpsql(Connection connection) {
        this.connection = connection;
    }

    public void setOdao(OVChipkaartDAO odao) {
        this.odao = odao;
    }

    @Override
    public boolean save(Product product) {
        try {
            String sqlQuery = "INSERT INTO product VALUES(?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, product.getProduct_nummer());
            ps.setString(2, product.getNaam());
            ps.setString(3, product.getBeschrijving());
            ps.setInt(4, product.getPrijs());

            ps.execute();
            ps.close();

            for (ov_chipkaart_product perOVProduct : product.getAlleOVProducten()) {
                String sqlQuery2 = "INSERT INTO ov_chipkaart_product VALUES(?, ?, ?, ?)";
                PreparedStatement ps2 = connection.prepareStatement(sqlQuery2);
                ps2.setInt(1, perOVProduct.getOvChipkaart().getKaart_nummer());
                ps2.setInt(2, perOVProduct.getProduct().getProduct_nummer());
                ps2.setString(3, perOVProduct.getStatus());
                ps2.setDate(4, perOVProduct.getLast_update());

                ps2.execute();
                ps2.close();
            }

            return true;

        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        try {
            String sqlQuery = "UPDATE product SET naam=?, beschrijving=?, prijs=? WHERE product_nummer=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setString(1, product.getNaam());
            ps.setString(2, product.getBeschrijving());
            ps.setInt(3, product.getPrijs());
            ps.setInt(4, product.getProduct_nummer());

            for(ov_chipkaart_product perOVProduct : product.getAlleOVProducten()) {
                String sqlQuery2 = "UPDATE ov_chipkaart_product SET status=?, last_update=? WHERE product_nummer=?";
                PreparedStatement ps2 = connection.prepareStatement(sqlQuery2);
                ps2.setString(1, perOVProduct.getStatus());
                ps2.setDate(2, perOVProduct.getLast_update());
                ps2.setInt(3, perOVProduct.getProduct().getProduct_nummer());

                ps2.execute();
                ps2.close();
            }

            ps.execute();
            ps.close();
            return true;

        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        try {
            String sqlQuery = "DELETE FROM product WHERE product_nummer=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, product.getProduct_nummer());

            for(ov_chipkaart_product perOVProduct : product.getAlleOVProducten()) {
                String sqlQuery2 = "DELETE FROM ov_chipkaart_product WHERE product_nummer=?";
                PreparedStatement ps2 = connection.prepareStatement(sqlQuery2);
                ps2.setInt(1, perOVProduct.getProduct().getProduct_nummer());

                ps2.execute();
                ps2.close();
            }

            ps.execute();
            ps.close();
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return false;
        }
    }
}
