package com.perso.bio.model.category;

import com.perso.bio.constants.Field;
import com.perso.bio.model.Product;
import jakarta.persistence.*;

import java.util.ArrayList;

import java.util.List;


@Entity
@Table(name = Field.BODY_PART)
public class BodyPart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bodyId;

    private String bodyName;

    @ManyToMany(mappedBy = Field.BODY_PARTS)
    private List<Product> products = new ArrayList<>();

    public BodyPart() {

    }

    public BodyPart(String bodyName) {
        this.bodyName = bodyName;
    }

    public Integer getBodyId() {
        return bodyId;
    }

    public void setBodyId(Integer bodyId) {
        this.bodyId = bodyId;
    }

    public String getBodyName() {
        return bodyName;
    }

    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }
}
