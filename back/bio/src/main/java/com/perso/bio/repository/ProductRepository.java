package com.perso.bio.repository;

import com.perso.bio.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByProductTypesTypeIdAndBodyPartsBodyId(Integer typeId, Integer bodyId);

    List<Product> findByProductTypesTypeId(Integer typeId);

    List<Product> findByBodyPartsBodyId(Integer bodyId);
}
