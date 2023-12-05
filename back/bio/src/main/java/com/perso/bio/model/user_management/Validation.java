package com.perso.bio.model.user_management;

import com.perso.bio.constants.Field;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = Field.VALIDATION)
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int validationId;
    private Instant creation;
    private Instant expire;
    private Instant activation;
    private String code;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = Field.USER_ID)
    private User user;

    public Validation() {

    }

    public Validation(Instant creation, Instant expire, Instant activation, String code, User user) {
        this.creation = creation;
        this.expire = expire;
        this.activation = activation;
        this.code = code;
        this.user = user;
    }

    public int getValidationId() {
        return validationId;
    }

    public void setValidationId(int validationId) {
        this.validationId = validationId;
    }

    public Instant getCreation() {
        return creation;
    }

    public void setCreation(Instant creation) {
        this.creation = creation;
    }

    public Instant getExpire() {
        return expire;
    }

    public void setExpire(Instant expire) {
        this.expire = expire;
    }

    public Instant getActivation() {
        return activation;
    }

    public void setActivation(Instant activation) {
        this.activation = activation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
