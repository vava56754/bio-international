package com.perso.bio.service.product;


import com.perso.bio.constants.MessageConstants;
import com.perso.bio.controller.StorageController;
import com.perso.bio.model.Product;
import com.perso.bio.model.category.BodyPart;
import com.perso.bio.model.category.ProductType;
import com.perso.bio.repository.BodyPartRepository;
import com.perso.bio.repository.ProductRepository;
import com.perso.bio.repository.ProductTypeRepository;
import com.perso.bio.service.storage.FilesStorageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;


import java.io.FileNotFoundException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class ProductServiceImpl implements ProductService {

    FilesStorageService filesStorageService;

    ProductRepository productRepository;

    ProductTypeRepository productTypeRepository;

    BodyPartRepository bodyPartRepository;

    @Autowired
    public ProductServiceImpl(FilesStorageService filesStorageService, ProductRepository productRepository,
                              ProductTypeRepository productTypeRepository, BodyPartRepository bodyPartRepository) {
        this.filesStorageService = filesStorageService;
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.bodyPartRepository = bodyPartRepository;
    }

    @Override
    public void createProduct(Product product, MultipartFile file) throws FileNotFoundException {
        saveFile(product, file);
        if (product.getProductTypes() != null) {
            setProductTypeForProduct(product);
        }

        if (product.getBodyParts() != null) {
            setBodyPartForProduct(product);
        }
        this.productRepository.save(product);
    }

    @Override
    public Product getProduct(Integer productId) throws MalformedURLException {
        Product product = this.productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException(MessageConstants.PRODUCT_SERVICE_ERROR_MESSAGE + productId));
        String url = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder
                        .on(StorageController.class)
                        .getFile(product.getProductLink()))
                .build()
                .toUriString();
        product.setProductLink(url);

        return product;
    }

    @Override
    public List<Product> getProductByTypeAndBodyPart(Integer typeId, Integer bodyId) {
        if (typeId != -1 && bodyId != -1) {
            return this.productRepository.findByProductTypesTypeIdAndBodyPartsBodyId(typeId, bodyId);
        } else if (typeId != -1) {
            return productRepository.findByProductTypesTypeId(typeId);
        } else if (bodyId != -1) {

            return productRepository.findByBodyPartsBodyId(bodyId);
        } else {

            return productRepository.findAll();
        }
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> products = this.productRepository.findAll();
        products.forEach(product -> {
            String url = null;
            try {
                url = MvcUriComponentsBuilder
                        .fromMethodCall(MvcUriComponentsBuilder
                                .on(StorageController.class)
                                .getFile(product.getProductLink()))
                        .build()
                        .toUriString();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            product.setProductLink(url);
        });
        return products;
    }

    @Override
    public void updateProduct(Integer productId, Product product, MultipartFile file) throws FileNotFoundException {
        Optional<Product> existingProduct = this.productRepository.findById(productId);
        if (existingProduct.isPresent()) {
            Product productUpdate = existingProduct.get();
            productUpdate.setProductName(Optional.ofNullable(product.getProductName())
                    .orElse(productUpdate.getProductName()));
            productUpdate.setProductDescription(Optional.ofNullable(product.getProductDescription())
                    .orElse(productUpdate.getProductDescription()));
            productUpdate.setProductUnitPrice(Optional.ofNullable(product.getProductUnitPrice())
                    .orElse(productUpdate.getProductUnitPrice()));
            product.setProductStock(Optional.ofNullable(product.getProductStock())
                    .orElse(productUpdate.getProductStock()));
            productUpdate.setHouse(Optional.ofNullable(product.getHouse())
                    .orElse(productUpdate.getHouse()));
            productUpdate.setProductTypes(Optional.ofNullable(product.getProductTypes())
                    .orElse(productUpdate.getProductTypes()));
            productUpdate.setBodyParts(Optional.ofNullable(product.getBodyParts())
                    .orElse(productUpdate.getBodyParts()));
            product.setProductId(productId);
            if (!file.isEmpty()) {
                deleteFileForProduct(productId);
                saveFile(productUpdate, file);
            }
            this.productRepository.save(productUpdate);
        } else {
            throw new EntityNotFoundException(MessageConstants.PRODUCT_SERVICE_ERROR_MESSAGE + productId);
        }

    }

    @Override
    public void deleteProduct(Integer productId) throws FileNotFoundException {
        Optional<Product> existingProduct = this.productRepository.findById(productId);
        if (existingProduct.isPresent()) {
            deleteFileForProduct(productId);
            this.productRepository.deleteById(productId);
        } else {
            throw new EntityNotFoundException(MessageConstants.PRODUCT_SERVICE_ERROR_MESSAGE + productId);
        }

    }

    private void deleteFileForProduct(Integer productId) throws FileNotFoundException {
        Product product = this.productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException(MessageConstants.PRODUCT_SERVICE_ERROR_MESSAGE + productId));
        try {
            this.filesStorageService.deleteByName(product.getProductLink());
        } catch (Exception e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    private void saveFile(Product product, MultipartFile file) throws FileNotFoundException {
        try {
            String filename = this.filesStorageService.save(file);
            product.setProductLink(filename);
        } catch (Exception e) {
            throw new FileNotFoundException(e.getMessage());
        }

    }

    private void setProductTypeForProduct(Product product) {
        List<ProductType> productTypes = new ArrayList<>();
        product.getProductTypes().forEach(productType -> {

            Optional<ProductType> existingProductType = productTypeRepository.findById(productType.getTypeId());
            if (existingProductType.isPresent()) {
                ProductType productTypePresent = existingProductType.get();
                productTypes.add(productTypePresent);
                product.setProductTypes(productTypes);
            } else {
                throw new EntityNotFoundException(MessageConstants.PRODUCT_TYPE_SERVICE_ERROR_MESSAGE + productType.getTypeId());
            }

        });
    }

    private void setBodyPartForProduct(Product product) {
        List<BodyPart> bodyParts = new ArrayList<>();
        product.getBodyParts().forEach(bodyPart -> {

            Optional<BodyPart> existingBodyPart = bodyPartRepository.findById(bodyPart.getBodyId());
            if (existingBodyPart.isPresent()) {
                BodyPart bodyPartPresent = existingBodyPart.get();
                bodyParts.add(bodyPartPresent);
                product.setBodyParts(bodyParts);
            } else {
                throw new EntityNotFoundException(MessageConstants.BODY_SERVICE_ERROR_MESSAGE + bodyPart.getBodyId());
            }


        });
    }
}
