package com.student.smartETailor.models;

import java.io.Serializable;

public class User implements Serializable {
    private String UID, email, password, name, contact, address, gender, imageURL, status, type,access;

    public User() {
    }

    public User(String UID, String name, String contact, String address,
                String gender, String imageURL, String status, String type,String access) {
        this.UID = UID;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.gender = gender;
        this.imageURL = imageURL;
        this.status = status;
        this.type = type;
        this.access = access;
    }

    public User(String UID, String email, String password,
                String name, String contact, String address,
                String gender, String imageURL, String status, String type,String access) {
        this.UID = UID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.gender = gender;
        this.imageURL = imageURL;
        this.status = status;
        this.type = type;
        this.access = access;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
