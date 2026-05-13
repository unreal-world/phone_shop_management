package com.mycompany.techstore.service;

import java.util.List;

import com.mycompany.techstore.model.Address;

public interface AddressService {
    List<Address> getAllAddresses();
    Address getAddressById(String addressID);
    List<Address> getAddressesByUserId(String userID);
    void saveAddress(Address address);
    void updateAddress(Address address);
    void deleteAddress(String addressID);
}
