package com.mycompany.phonestore.service.impl;

import com.mycompany.phonestore.dao.AddressDao;
import com.mycompany.phonestore.model.Address;
import com.mycompany.phonestore.service.AddressService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressDao.getAllAddresses();
    }

    @Override
    public Address getAddressById(String addressID) {
        return addressDao.getAddressById(addressID);
    }

    @Override
    public List<Address> getAddressesByUserId(String userID) {
        return addressDao.getAddressesByUserId(userID);
    }

    @Override
    public void saveAddress(Address address) {
        addressDao.addAddress(address);
    }

    @Override
    public void updateAddress(Address address) {
        addressDao.updateAddress(address);
    }

    @Override
    public void deleteAddress(String addressID) {
        addressDao.deleteAddress(addressID);
    }

    @Override
    public void addAddress(Address newAddress) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
