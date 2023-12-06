package com.perso.bio.dto;

public class ProductDTO {

    private Integer productId;
    private String productName;
    private Integer productStock;
    private String productLink;
    private Integer productUnitPrice;

    public ProductDTO() {

    }

    public ProductDTO(String productName, Integer productStock, String productLink, Integer productUnitPrice) {
        this.productName = productName;
        this.productStock = productStock;
        this.productLink = productLink;
        this.productUnitPrice = productUnitPrice;
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

    public Integer getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Integer productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }
}
