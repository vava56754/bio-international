package com.perso.bio.model.procurement;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.perso.bio.constants.Field;
import com.perso.bio.model.Product;
import jakarta.persistence.*;

@Entity
@Table(name = Field.LINE_PROCUREMENT)
public class LineProcurement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer lineId;
    private Integer lineUnitPrice;
    private Integer lineQuantity;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = Field.PROCUREMENT_ID)
    private Procurement procurement;

    @OneToOne
    @JoinColumn(name = Field.PRODUCT_ID)
    private Product product;

    public LineProcurement() {

    }

    public LineProcurement(Integer lineId, Integer lineUnitPrice, Integer lineQuantity) {
        this.lineId = lineId;
        this.lineUnitPrice = lineUnitPrice;
        this.lineQuantity = lineQuantity;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getLineUnitPrice() {
        return lineUnitPrice;
    }

    public void setLineUnitPrice(Integer lineUnitPrice) {
        this.lineUnitPrice = lineUnitPrice;
    }

    public Integer getLineQuantity() {
        return lineQuantity;
    }

    public void setLineQuantity(Integer lineQuantity) {
        this.lineQuantity = lineQuantity;
    }

    public Procurement getProcurement() {
        return procurement;
    }

    public void setProcurement(Procurement procurement) {
        this.procurement = procurement;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
