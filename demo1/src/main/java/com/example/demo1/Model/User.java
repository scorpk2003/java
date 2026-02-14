package com.example.demo1.Model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "User")
public class User {


    private int ID;

    private String Name;
    @NotNull
    private String Email;

    private String Password;

    private String Phone;

    private Date Birth;

    private double CCCD;

    private String Address;

    private String Gender;

    private boolean Admin;

    @Column(name = "Admin")
    public boolean isAdmin() {
        return Admin;
    }

    public void setAdmin(boolean admin) {
        Admin = admin;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Column(name = "Name")
    @NotNull
    public String getName() {
        return Name;
    }

    public void setName(@NotNull String name) {
        Name = name;
    }

    @Column(name = "Email")

    public String getEmail() {
        return Email;
    }

    public void setEmail(@NotNull String email) {
        Email = email;
    }

    @Column(name = "Password")
    @NotNull
    public String getPassword() {
        return Password;
    }

    public void setPassword(@NotNull String password) {
        Password = password;
    }

    @Column(name = "Phone")
    @NotNull
    public String getPhone() {
        return Phone;
    }

    public void setPhone(@NotNull String phone) {
        Phone = phone;
    }

    @Column(name = "Birth")
    @NotNull
    public Date getBirth() {
        return Birth;
    }

    public void setBirth(@NotNull Date birth) {
        Birth = birth;
    }

    @Column(name = "CCCD")
    public double getCCCD() {
        return CCCD;
    }

    public void setCCCD(double CCCD) {
        this.CCCD = CCCD;
    }
    @NotNull

    @Column(name = "Address")
    public String getAddress() {
        return Address;
    }

    public void setAddress(@NotNull String address) {
        Address = address;
    }

    @Column(name = "Gender")
    @NotNull
    public String getGender() {
        return Gender;
    }

    public void setGender(@NotNull String gender) {
        Gender = gender;
    }
}
