package com.example.dell.qltc.datamodel;

public class UserAccount {
    private String email;
    private String password;
    private String timeCreate;

    public UserAccount() {
    }

    public UserAccount(String email, String password, String timeCreate) {
        this.email = email;
        this.password = password;
        this.timeCreate = timeCreate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", timeCreate='" + timeCreate + '\'' +
                '}';
    }
}
