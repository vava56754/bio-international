package com.perso.bio.model.category;

import com.perso.bio.constants.Field;
import com.perso.bio.model.Product;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;

import java.util.List;


@Entity
@Table(name = Field.PRODUCT_TYPE)
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer typeId;

    private String typeName;

    @ManyToMany
    @JoinTable(
            name = Field.PRODUCT_PRODUCT_TYPE,
            joinColumns = @JoinColumn(name = Field.PRODUCT_TYPE_ID),
            inverseJoinColumns = @JoinColumn(name = Field.PRODUCT_ID))
    private List<Product> products = new ArrayList<>();

    public ProductType() {

    }

    public ProductType(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
