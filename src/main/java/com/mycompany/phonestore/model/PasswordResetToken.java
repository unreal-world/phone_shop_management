package com.mycompany.phonestore.model;

import java.time.LocalDateTime;

public class PasswordResetToken {
    private String tokenID;
    private String token;
    private String userID;
    private LocalDateTime expiryDate;
    private boolean isUsed;

    public PasswordResetToken() {}

    public PasswordResetToken(String tokenID, String token, String userID, LocalDateTime expiryDate, boolean isUsed) {
        this.tokenID = tokenID;
        this.token = token;
        this.userID = userID;
        this.expiryDate = expiryDate;
        this.isUsed = isUsed;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }

    public String getTokenID() { return tokenID; }
    public void setTokenID(String tokenID) { this.tokenID = tokenID; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }

    public boolean isUsed() { return isUsed; }
    public void setUsed(boolean used) { isUsed = used; }
}
