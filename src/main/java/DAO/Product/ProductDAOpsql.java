package DAO.Product;

import DAO.OVChipkaart.OVChipkaartDAO;
import Domein.Product;

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

            ps.execute();
            ps.close();
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return false;
        }
    }
}
