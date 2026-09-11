package com.mycompany.phonestore.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.phonestore.dao.PasswordResetTokenDao;
import com.mycompany.phonestore.model.PasswordResetToken;

@Repository
public class PasswordResetTokenDaoImpl implements PasswordResetTokenDao {

    private final JdbcTemplate jdbcTemplate;

    public PasswordResetTokenDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(PasswordResetToken token) {
        String sql = "INSERT INTO PasswordResetToken (tokenID, token, userID, expiryDate, isUsed) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                token.getTokenID(),
                token.getToken(),
                token.getUserID(),
                Timestamp.valueOf(token.getExpiryDate()),
                token.isUsed()
        );
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        String sql = "SELECT * FROM PasswordResetToken WHERE token = ?";
        List<PasswordResetToken> list = jdbcTemplate.query(sql, new Object[]{token}, (rs, rowNum) -> {
            PasswordResetToken t = new PasswordResetToken();
            t.setTokenID(rs.getString("tokenID"));
            t.setToken(rs.getString("token"));
            t.setUserID(rs.getString("userID"));
            t.setExpiryDate(rs.getTimestamp("expiryDate").toLocalDateTime());
            t.setUsed(rs.getBoolean("isUsed"));
            return t;
        });
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void markUsed(String token) {
        String sql = "UPDATE PasswordResetToken SET isUsed = TRUE WHERE token = ?";
        jdbcTemplate.update(sql, token);
    }
}
