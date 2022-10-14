package DAO.Product;

import DAO.OVChipkaart.OVChipkaartDAO;
import Domein.OVChipkaart;
import Domein.Product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

            for (Integer perOVChipkaart : product.getAlleOVChipkaarten()) {
                String sqlQuery2 = "INSERT INTO ov_chipkaart_product VALUES(?, ?, ?, ?)";
                PreparedStatement ps2 = connection.prepareStatement(sqlQuery2);
                ps2.setInt(1, perOVChipkaart);
                ps2.setInt(2, product.getProduct_nummer());
                ps2.setString(3, "positive");
                ps2.setDate(4, Date.valueOf("2000-01-01"));

                ps2.execute();
                ps2.close();

                // do I really have to do this
//                odao.save(perOVChipkaart);
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

//          dit moet weg want product is child anders stack overflow
            for(Integer perOVChipkaart : product.getAlleOVChipkaarten()) {
                String sqlQuery2 = "UPDATE ov_chipkaart_product SET status=?, last_update=? WHERE product_nummer=?";
                PreparedStatement ps2 = connection.prepareStatement(sqlQuery2);
                ps2.setString(1, "negative");
                ps2.setDate(2, Date.valueOf("2001-01-01"));
                ps2.setInt(3, product.getProduct_nummer());

                ps2.execute();
                ps2.close();

                // Here again do I have to update ovChipkaart
//                odao.update(perOVChipkaart);
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

            ps.execute();
            ps.close();

            for(Integer perOVChipkaart : product.getAlleOVChipkaarten()) {
                String sqlQuery2 = "DELETE FROM ov_chipkaart_product WHERE product_nummer=?";
                PreparedStatement ps2 = connection.prepareStatement(sqlQuery2);
                ps2.setInt(1, product.getProduct_nummer());

                ps2.execute();
                ps2.close();

                // And here too I don't know if this is right
//                odao.delete(perOVChipkaart);
            }

            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return false;
        }
    }

    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            String sqlQuery = "select * from product p join ov_chipkaart_product ocp on p.product_nummer = ocp.product_nummer where kaart_nummer=?";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, ovChipkaart.getKaart_nummer());

            List<Product> allProducts = new ArrayList<>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                allProducts.add(product);
            }

//            voeg de ovchipkaarten aan product

            ps.close();
            rs.close();
            return allProducts;

        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return null;
        }
    }

    public List<Product> findAll() {
        try {
            String sqlQuery = "select * from product";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);

            ResultSet rs = ps.executeQuery();
            List<Product> allProducts = new ArrayList<>();

            while (rs.next()) {
                Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                allProducts.add(product);
            }

//            voeg de ovchipkaarten aan product

            return allProducts;
        } catch(Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
            return null;
        }
    }
}
