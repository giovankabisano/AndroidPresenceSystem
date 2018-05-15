package com.example.giovankabisano.androidpresencesystem;

public class UserAPK {
    String displayName;
    String password;
    String email;
    String tipe;
    long createdAt;

    public UserAPK(){}

    public UserAPK(String displayName, String email, String password, long createdAt) {
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.tipe = "customer";
        this.createdAt = createdAt;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
