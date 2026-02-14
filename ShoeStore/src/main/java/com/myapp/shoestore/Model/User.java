package com.myapp.shoestore.Model;

import java.util.Date;

public class User {

    private String UserID;

    private String Name;

    private String Email;

    private String Pass;

    private String Phone;

    private Date Birth;

    private String CCCD;

    private String Address;

    private String Gender;

    private boolean Admin;

    public User(String userID, String name, String email, String pass, String phone, Date birth, String CCCD, String address, String gender, boolean admin) {
        UserID = userID;
        Name = name;
        Email = email;
        Pass = pass;
        Phone = phone;
        Birth = birth;
        this.CCCD = CCCD;
        Address = address;
        Gender = gender;
        Admin = admin;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public java.sql.Date getBirth() {
        return (java.sql.Date) Birth;
    }

    public void setBirth(Date birth) {
        Birth = birth;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public boolean isAdmin() {
        return Admin;
    }

    public void setAdmin(boolean admin) {
        Admin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserID='" + UserID + '\'' +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Pass='" + Pass + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Birth=" + Birth +
                ", CCCD=" + CCCD +
                ", Address='" + Address + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Admin=" + Admin +
                '}';
    }
}
