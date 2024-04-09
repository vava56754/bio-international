package com.perso.bio.model.user_management;

import com.perso.bio.constants.Field;
import jakarta.persistence.*;


@Entity
@Table(name = Field.JWT)
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int jwtId;

    @Column(length = 1000)
    private String jwtValue;

    private boolean jwtDisable;
    private boolean jwtExpire;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = Field.USER_ID)
    private User user;


    public static class Builder {
        private final String jwtValue;
        private boolean jwtDisable;
        private boolean jwtExpire;
        @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
        @JoinColumn(name = Field.USER_ID)
        private User user;

        public Builder(String jwtValue, User user) {
            this.jwtValue = jwtValue;
            this.user = user;
        }

        public Builder disable(boolean jwtDisable) {
            this.jwtDisable = jwtDisable;
            return this;
        }

        public Builder expire(boolean jwtExpire) {
            this.jwtExpire = jwtExpire;
            return this;
        }

        public Jwt build() {
            return new Jwt(this);
        }
    }

    private Jwt(Builder builder) {
        this.jwtValue = builder.jwtValue;
        this.jwtDisable = builder.jwtDisable;
        this.jwtExpire = builder.jwtExpire;
        this.user = builder.user;
    }

    private Jwt() {

    }

    public int getJwtId() {
        return jwtId;
    }

    public void setJwtId(int jwtId) {
        this.jwtId = jwtId;
    }

    public String getJwtValue() {
        return jwtValue;
    }

    public void setJwtValue(String jwtValue) {
        this.jwtValue = jwtValue;
    }

    public boolean isJwtDisable() {
        return jwtDisable;
    }

    public void setJwtDisable(boolean jwtDisable) {
        this.jwtDisable = jwtDisable;
    }

    public boolean isJwtExpire() {
        return jwtExpire;
    }

    public void setJwtExpire(boolean jwtExpire) {
        this.jwtExpire = jwtExpire;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
