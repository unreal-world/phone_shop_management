package com.mycompany.techstore.model;

public class Address {
    private String addressID;
    private String city;
    private String ward;
    private String street;
    private String houseNumber;

    public Address() {}

    public Address(String addressID, String city, String ward, String street, String houseNumber) {
        this.addressID = addressID;
        this.city = city;
        this.ward = ward;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
