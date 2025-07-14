package com.example.prm392_shopping_project.model;

import java.sql.Date;

public class Account {
    private String email;
    private int password;
    private Date createdAt;
    private boolean isAdmin;
    public Account() {

    }

    public Account(String email, int password, Date createdAt, boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
