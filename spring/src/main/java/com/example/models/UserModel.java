package com.example.models;

import javax.persistence.*;

@Entity
@Table
public class UserModel {

    public static final String USER = "USER", MANAGER = "MANAGER", ADMIN = "ADMIN";

    @Id
    private String email;

    private String password;
    private String username;
    private String mobileNumber;
    private boolean active;
    private String role = UserModel.USER;

    public UserModel() {
    }

    public UserModel(String email, String password, String username, String mobileNumber, boolean active, String role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.active = active;
        this.role = role;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public List<ExpenseModel> getExpenseModelList() {
//        return expenseModelList;
//    }
//
//    public void setExpenseModelList(List<ExpenseModel> expenseModelList) {
//        this.expenseModelList = expenseModelList;
//    }

    @Override
    public String toString() {
        return "UserModel{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", active=" + active +
                ", role='" + role + '\'' +
                '}';
    }
}
