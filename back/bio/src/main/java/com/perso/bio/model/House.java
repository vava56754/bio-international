package com.perso.bio.model;

import com.perso.bio.constants.Field;
import jakarta.persistence.*;

@Entity
@Table(name = Field.HOUSE)
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int houseId;
    private String houseName;

    @Column(length = 1000)
    private String houseDescription;
    private String houseLink;


    public House() {

    }

    public House(int houseId, String houseName, String houseDescription, String houseLink) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.houseDescription = houseDescription;
        this.houseLink = houseLink;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseDescription() {
        return houseDescription;
    }

    public void setHouseDescription(String houseDescription) {
        this.houseDescription = houseDescription;
    }

    public String getHouseLink() {
        return houseLink;
    }

    public void setHouseLink(String houseLink) {
        this.houseLink = houseLink;
    }
}
