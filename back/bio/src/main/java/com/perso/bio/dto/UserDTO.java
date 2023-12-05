package com.perso.bio.dto;

public class UserDTO {
    private String usernameDTO;
    private String nameDTO;
    private String firstNameDTO;
    private String phoneDTO;
    private String addressDTO;
    private String postalCodeDTO;
    private String cityDTO;
    private String countryDTO;

    public UserDTO() {

    }

    public String getUsernameDTO() {
        return usernameDTO;
    }

    public void setUsernameDTO(String usernameDTO) {
        this.usernameDTO = usernameDTO;
    }

    public String getNameDTO() {
        return nameDTO;
    }

    public void setNameDTO(String nameDTO) {
        this.nameDTO = nameDTO;
    }

    public String getFirstNameDTO() {
        return firstNameDTO;
    }

    public void setFirstNameDTO(String firstNameDTO) {
        this.firstNameDTO = firstNameDTO;
    }

    public String getPhoneDTO() {
        return phoneDTO;
    }

    public void setPhoneDTO(String phoneDTO) {
        this.phoneDTO = phoneDTO;
    }

    public String getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(String addressDTO) {
        this.addressDTO = addressDTO;
    }

    public String getPostalCodeDTO() {
        return postalCodeDTO;
    }

    public void setPostalCodeDTO(String postalCodeDTO) {
        this.postalCodeDTO = postalCodeDTO;
    }

    public String getCityDTO() {
        return cityDTO;
    }

    public void setCityDTO(String cityDTO) {
        this.cityDTO = cityDTO;
    }

    public String getCountryDTO() {
        return countryDTO;
    }

    public void setCountryDTO(String countryDTO) {
        this.countryDTO = countryDTO;
    }
}
