package com.mycompany.phonestore.dao;

import com.mycompany.phonestore.model.PasswordResetToken;

public interface PasswordResetTokenDao {
    void save(PasswordResetToken token);
    PasswordResetToken findByToken(String token);
    void markUsed(String token);
}
