package com.perso.bio.dto;

public class LineProcurementDTO {

    private Integer lineId;
    private Integer lineQuantity;
    private Integer lineUnitPrice;
    private ProductDTO product;

    public LineProcurementDTO() {

    }

    public LineProcurementDTO(int lineQuantity) {
        this.lineQuantity = lineQuantity;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public int getLineQuantity() {
        return lineQuantity;
    }

    public void setLineQuantity(int lineQuantity) {
        this.lineQuantity = lineQuantity;
    }

    public Integer getLineUnitPrice() {
        return lineUnitPrice;
    }

    public void setLineUnitPrice(Integer lineUnitPrice) {
        this.lineUnitPrice = lineUnitPrice;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
