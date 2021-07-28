package com.example.appchatrealtime.model;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.Date;
import java.util.regex.Pattern;

public class User {
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String date;
    private String linkPhoto;

    public User() {
    }

    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber="";
        this.date="";
        this.linkPhoto="";
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.fullName = "";
        this.phoneNumber="";
        this.date="";
        this.linkPhoto="";
    }

    public User(String email, String password, String fullName, String phoneNumber, String date, String linkPhoto) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.linkPhoto = linkPhoto;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }

    public boolean IsValidEmail(){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean IsValidPassword(){
        return !TextUtils.isEmpty(password) && password.length()>=6;
    }
    public boolean IsValidFUllName(){
        return !TextUtils.isEmpty(fullName) ;
    }

}
