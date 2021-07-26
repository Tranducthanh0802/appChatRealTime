package com.example.appchatrealtime.model;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.Date;
import java.util.regex.Pattern;

public class User {
    private String Email;
    private String Password;
    private String FullName;
    private String PhoneNumber;
    private String Date;
    private String LinkPhoto;

    public User() {
    }

    public User(String email, String password) {
        Email = email;
        Password = password;
        FullName = "";
        PhoneNumber = "";
        Date = "";
        LinkPhoto = "";
    }

    public User(String email, String password, String fullName, String phoneNumber, String date, String linkPhoto) {
        Email = email;
        Password = password;
        FullName = fullName;
        PhoneNumber = phoneNumber;
        Date = date;
        LinkPhoto = linkPhoto;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLinkPhoto() {
        return LinkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        LinkPhoto = linkPhoto;
    }

    public boolean IsValidEmail(){
        return !TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }
    public boolean IsValidPassword(){
        return !TextUtils.isEmpty(Password) && Password.length()>=6;
    }

}
