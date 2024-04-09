package com.perso.bio.repository;

import com.perso.bio.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductIdAndIsVisibleTrue(Integer productId);

    List<Product> findByProductTypesTypeIdAndBodyPartsBodyIdAndIsVisibleTrueOrderByProductNameAsc(Integer typeId, Integer bodyId);

    List<Product> findByIsVisibleTrueOrderByProductNameAsc();

    List<Product> findByProductTypesTypeIdAndIsVisibleTrueOrderByProductNameAsc(Integer typeId);

    List<Product> findByBodyPartsBodyIdAndIsVisibleTrueOrderByProductNameAsc(Integer bodyId);

    List<Product> findByProductNameContainingIgnoreCaseAndIsVisibleTrue(String name);

    List<Product> findByHouseHouseId(Integer id);
}
