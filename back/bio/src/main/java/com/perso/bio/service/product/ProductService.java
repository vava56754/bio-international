package com.perso.bio.service.product;

import com.perso.bio.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

public interface ProductService {

    void createProduct(Product product, MultipartFile file) throws FileNotFoundException;

    void updateProduct(Integer productId, Product product, MultipartFile file) throws FileNotFoundException;

    Product getProduct(Integer productId) throws MalformedURLException;

    List<Product> getProductByTypeAndBodyPart(Integer typeId, Integer bodyId);

    List<Product> getAllProduct();

    void deleteProduct(Integer productId) throws FileNotFoundException;
}
