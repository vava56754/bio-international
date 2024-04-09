package com.perso.bio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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


    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(ProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createProduct(@RequestPart("File") MultipartFile file, @RequestPart("product") String product) throws FileNotFoundException, JsonProcessingException {
        Product productObj;

        productObj = objectMapper.readValue(product, Product.class);
        this.productService.createProduct(productObj, file);

        return new ResponseEntity<>(MessageConstants.CREATE, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) throws MalformedURLException {
        Product product = this.productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping(path = "/visible/{id}")
    public ResponseEntity<Product> getProductVisible(@PathVariable Integer id) throws MalformedURLException {
        Product product = this.productService.getProductVisible(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        List<Product> products = this.productService.searchProducts(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = this.productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping(path = "/type/{typeId}/body/{bodyId}")
    public ResponseEntity<List<Product>> getAllProductsByTypeAndBody(@PathVariable Integer typeId, @PathVariable Integer bodyId) {
        List<Product> products = this.productService.getProductsByTypeAndBodyPart(typeId, bodyId);
        return ResponseEntity.ok(products);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id, @RequestPart("File") MultipartFile file, @RequestPart("product") String product) throws FileNotFoundException, JsonProcessingException {
        Product productObj;

        productObj = objectMapper.readValue(product, Product.class);
        this.productService.updateProduct(id, productObj, file);

        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.OK);
    }

    @PutMapping(path = "/update/visible/{id}")
    public ResponseEntity<String> productVisible(@PathVariable Integer id) {
        this.productService.updateIsVisible(id);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) throws FileNotFoundException {
        this.productService.deleteProduct(id);
        return new ResponseEntity<>(MessageConstants.DELETE, HttpStatus.NO_CONTENT);
    }


}
