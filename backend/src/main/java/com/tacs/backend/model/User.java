package com.tacs.backend.model;

/**
 * @author tianshuwang
 */
public class User {

    private String username;
    private String password;

    public User() {
        // TODO document why this constructor is empty
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
