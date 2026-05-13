package com.mycompany.techstore.dao;

import java.util.List;

import com.mycompany.techstore.model.Address;

public interface AddressDao {
    List<Address> getAllAddresses();
    Address getAddressById(String addressID);
    List<Address> getAddressesByUserId(String userID);
    void addAddress(Address address);
    void updateAddress(Address address);
    void deleteAddress(String addressID);
}
