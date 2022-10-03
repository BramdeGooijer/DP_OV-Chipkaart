package Domein;

import java.util.Date;

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

    public String toString() {
        return String.format("ov_chipkaart_product {%s, %s}", this.status, this.last_update);
    }
}
