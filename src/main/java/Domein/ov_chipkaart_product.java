package Domein;

import java.sql.Date;

public class ov_chipkaart_product {
    private OVChipkaart ovChipkaart;
    private Product product;
    private String status;
    private Date last_update;

    public ov_chipkaart_product(OVChipkaart ovChipkaart, Product product, String status, Date last_update) {
        this.ovChipkaart = ovChipkaart;
        this.product = product;
        this.status = status;
        this.last_update = last_update;
        ovChipkaart.add_ov_chipkaart_product(this);
        product.add_ov_chipkaart_product(this);
    }

    public OVChipkaart getOvChipkaart() {
        return this.ovChipkaart;
    }

    public Product getProduct() {
        return this.product;
    }

    public String getStatus() {
        return this.status;
    }

    public Date getLast_update() {
        return this.last_update;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public String toString() {
        return String.format("ov_chipkaart_product {%s, %s}", this.status, this.last_update);
    }
}
