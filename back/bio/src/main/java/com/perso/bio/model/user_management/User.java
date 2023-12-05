package com.perso.bio.model.user_management;

import com.perso.bio.constants.Field;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = Field.USER)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String userName;
    private String userPhone;
    private String userFirstName;
    private String userAddress;
    private String userPostalCode;
    private String userCity;
    private String userCountry;
    private String userMail;
    private String userPassword;
    private Boolean enable = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = Field.ROLE_ID)
    private Role role;


    //default constructor
    public User() {

    }

    public User(String name, String firstName, String phone, String address, String postalCode, String city,
                String country, String mail, String password, boolean enable) {
        this.userName = name;
        this.userFirstName = firstName;
        this.userPhone = phone;
        this.userAddress = address;
        this.userPostalCode = postalCode;
        this.userCity = city;
        this.userCountry = country;
        this.userMail = mail;
        this.userPassword = password;
        this.enable = enable;


    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPostalCode() {
        return userPostalCode;
    }

    public void setUserPostalCode(String userPostalCode) {
        this.userPostalCode = userPostalCode;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(Field.ROLE_ + this.role.getRoleName().name()));
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userMail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enable;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enable;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.enable;
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }
}
