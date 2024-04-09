package com.perso.bio.model;

import com.perso.bio.constants.Field;
import com.perso.bio.model.category.BodyPart;
import com.perso.bio.model.category.ProductType;
import jakarta.persistence.*;

import java.util.ArrayList;

import java.util.List;


@Entity
@Table(name = Field.PRODUCT)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;
    private String productName;

    @Column(length = 1000)
    private String productDescription;
    private Integer productUnitPrice;
    private Integer productStock;
    private String productLink;
    private Boolean isVisible;

    @ManyToOne
    @JoinColumn(name = Field.HOUSE_ID)
    private House house;

    @ManyToMany
    @JoinTable(
            name = Field.PRODUCT_PRODUCT_TYPE,
            joinColumns = @JoinColumn(name = Field.PRODUCT_ID),
            inverseJoinColumns = @JoinColumn(name = Field.PRODUCT_TYPE_ID))
    private List<ProductType> productTypes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = Field.PRODUCT_BODY_PART,
            joinColumns = @JoinColumn(name = Field.PRODUCT_ID),
            inverseJoinColumns = @JoinColumn(name = Field.BODY_PART_ID))
    private List<BodyPart> bodyParts = new ArrayList<>();

    protected Product() {
    }

    public Product(String productName, String productDescription, Integer productUnitPrice, Integer productStock, String productLink, boolean isVisible) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productUnitPrice = productUnitPrice;
        this.productStock = productStock;
        this.productLink = productLink;
        this.isVisible = isVisible;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Integer getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Integer productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public List<BodyPart> getBodyParts() {
        return bodyParts;
    }

    public void setBodyParts(List<BodyPart> bodyParts) {
        this.bodyParts = bodyParts;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }
}
