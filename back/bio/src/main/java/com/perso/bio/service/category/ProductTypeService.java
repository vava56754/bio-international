package com.perso.bio.service.category;

import com.perso.bio.model.category.ProductType;

import java.util.List;

public interface ProductTypeService {

    void createProductType(ProductType productType);

    ProductType getProductType(Integer typeId);

    List<ProductType> getAllProductType();

    void updateProductType(Integer typeId, ProductType productType);

    void deleteProductType(Integer typeId);

}
