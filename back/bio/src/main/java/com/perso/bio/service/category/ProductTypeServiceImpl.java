package com.perso.bio.service.category;


import com.perso.bio.constants.MessageConstants;
import com.perso.bio.model.category.ProductType;
import com.perso.bio.repository.ProductTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Qualifier("jpa")
public class ProductTypeServiceImpl implements ProductTypeService {

    ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public void createProductType(ProductType productType) {
        this.productTypeRepository.save(productType);
    }

    @Override
    public ProductType getProductType(Integer typeId) {
        return this.productTypeRepository.findById(typeId).orElseThrow(() -> new EntityNotFoundException(MessageConstants.PRODUCT_TYPE_SERVICE_ERROR_MESSAGE + typeId));
    }

    @Override
    public List<ProductType> getAllProductType() {
        return this.productTypeRepository.findAll();
    }

    @Override
    public void updateProductType(Integer typeId, ProductType productType) {
        Optional<ProductType> existingProductType = this.productTypeRepository.findById(typeId);
        if (existingProductType.isPresent()) {
            ProductType currentProductType = existingProductType.get();
            currentProductType.setTypeName(productType.getTypeName());
            productTypeRepository.save(currentProductType);
        } else {
            throw new EntityNotFoundException(MessageConstants.PRODUCT_TYPE_SERVICE_ERROR_MESSAGE + typeId);
        }
    }

    @Override
    public void deleteProductType(Integer typeId) {
        Optional<ProductType> existingProductType = this.productTypeRepository.findById(typeId);
        if (existingProductType.isPresent()) {
            productTypeRepository.deleteById(typeId);
        } else {
            throw new EntityNotFoundException(MessageConstants.PRODUCT_TYPE_SERVICE_ERROR_MESSAGE + typeId);
        }
    }


}
