package com.mycompany.phonestore.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.phonestore.dao.AddressDao;
import com.mycompany.phonestore.model.Address;

@Repository
public class AddressDaoImpl implements AddressDao {

    private final JdbcTemplate jdbcTemplate;

    public AddressDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Address> getAllAddresses() {
        String sql = "SELECT * FROM Address";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Address(
                rs.getString("addressID"),
                rs.getString("userID"),
                rs.getString("city"),
                rs.getString("ward"),
                rs.getString("street"),
                rs.getString("houseNumber")
        ));
    }

    @Override
    public Address getAddressById(String addressID) {
        String sql = "SELECT * FROM Address WHERE addressID = ?";
        List<Address> addresses = jdbcTemplate.query(sql, new Object[]{addressID}, (rs, rowNum) -> new Address(
                rs.getString("addressID"),
                rs.getString("userID"),
                rs.getString("city"),
                rs.getString("ward"),
                rs.getString("street"),
                rs.getString("houseNumber")
        ));
        return addresses.isEmpty() ? null : addresses.get(0);
    }

    @Override
    public List<Address> getAddressesByUserId(String userID) {
        String sql = "SELECT * FROM Address WHERE userID = ?";
        return jdbcTemplate.query(sql, new Object[]{userID}, (rs, rowNum) -> new Address(
                rs.getString("addressID"),
                rs.getString("userID"),
                rs.getString("city"),
                rs.getString("ward"),
                rs.getString("street"),
                rs.getString("houseNumber")
        ));
    }

    @Override
    public void addAddress(Address address) {
        String sql = "INSERT INTO Address (addressID, userID, city, ward, street, houseNumber) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, address.getAddressID(), address.getUserID(), address.getCity(), address.getWard(), address.getStreet(), address.getHouseNumber());
    }

    @Override
    public void updateAddress(Address address) {
        String sql = "UPDATE Address SET userID=?, city=?, ward=?, street=?, houseNumber=? WHERE addressID=?";
        jdbcTemplate.update(sql, address.getUserID(), address.getCity(), address.getWard(), address.getStreet(), address.getHouseNumber(), address.getAddressID());
    }

    @Override
    public void deleteAddress(String addressID) {
        String sql = "DELETE FROM Address WHERE addressID=?";
        jdbcTemplate.update(sql, addressID);
    }
}
