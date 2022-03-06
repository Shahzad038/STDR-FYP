package com.student.smartETailor.models;

public class Measurement {
    private String measurementID;
    private String neck, shoulders, sleeves, chest, waist, hips, inseam, thigh;
    private String payment;

    public Measurement() {
        payment = "";
    }

    public Measurement(String measurementID, String neck, String shoulders, String sleeves, String chest, String waist, String hips, String inseam, String thigh) {
        this.neck = neck;
        this.shoulders = shoulders;
        this.sleeves = sleeves;
        this.chest = chest;
        this.waist = waist;
        this.hips = hips;
        this.inseam = inseam;
        this.thigh = thigh;
        this.measurementID = measurementID;
        this.payment = "";
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getMeasurementID() {
        return measurementID;
    }

    public void setMeasurementID(String measurementID) {
        this.measurementID = measurementID;
    }

    public String getNeck() {
        return neck;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    public String getShoulders() {
        return shoulders;
    }

    public void setShoulders(String shoulders) {
        this.shoulders = shoulders;
    }

    public String getSleeves() {
        return sleeves;
    }

    public void setSleeves(String sleeves) {
        this.sleeves = sleeves;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getHips() {
        return hips;
    }

    public void setHips(String hips) {
        this.hips = hips;
    }

    public String getInseam() {
        return inseam;
    }

    public void setInseam(String inseam) {
        this.inseam = inseam;
    }

    public String getThigh() {
        return thigh;
    }

    public void setThigh(String thigh) {
        this.thigh = thigh;
    }
}
