package com.perso.bio.controller.category;

import com.perso.bio.constants.MessageConstants;
import com.perso.bio.model.category.ProductType;
import com.perso.bio.service.category.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/type")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createProductType(@RequestBody ProductType productType) {
        this.productTypeService.createProductType(productType);
        return new ResponseEntity<>(MessageConstants.CREATE, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateProductType(@PathVariable Integer id, @RequestBody ProductType productType) {
        this.productTypeService.updateProductType(id, productType);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteProductType(@PathVariable Integer id) {
        this.productTypeService.deleteProductType(id);
        return new ResponseEntity<>(MessageConstants.DELETE, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ProductType>> getAllProductType() {
        List<ProductType> productTypes = this.productTypeService.getAllProductType();
        return ResponseEntity.ok(productTypes);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductType> getProductType(@PathVariable Integer id) {
        ProductType productType = this.productTypeService.getProductType(id);
        return ResponseEntity.ok(productType);
    }


}
