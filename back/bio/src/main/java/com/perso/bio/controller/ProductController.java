package com.perso.bio.controller;

import com.perso.bio.constants.MessageConstants;
import com.perso.bio.model.Product;
import com.perso.bio.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createProduct(@RequestPart("file") MultipartFile file, @RequestPart("product") Product product) throws FileNotFoundException {
        this.productService.createProduct(product, file);
        return new ResponseEntity<>(MessageConstants.CREATE, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) throws MalformedURLException {
        Product product = this.productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> products = this.productService.getAllProduct();
        return ResponseEntity.ok(products);
    }

    @GetMapping(path = "/type/{typeId}/body/{bodyId}")
    public ResponseEntity<List<Product>> getAllProductByTypeAndBody(@PathVariable Integer typeId, @PathVariable Integer bodyId) {
        List<Product> products = this.productService.getProductByTypeAndBodyPart(typeId, bodyId);
        return ResponseEntity.ok(products);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id, @RequestPart("file") MultipartFile file, @RequestPart("product") Product product) throws FileNotFoundException {
        this.productService.updateProduct(id, product, file);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) throws FileNotFoundException {
            this.productService.deleteProduct(id);
            return new ResponseEntity<>(MessageConstants.DELETE, HttpStatus.NO_CONTENT);
    }


}
