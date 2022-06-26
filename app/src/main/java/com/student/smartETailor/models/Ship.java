package com.student.smartETailor.models;

public class Ship {
    private String riderID, expectedDate,deliveryDate, payment;

    public Ship() {
    }

    public Ship(String riderID, String expectedDate,String deliveryDate, String payment) {
        this.riderID = riderID;
        this.expectedDate = expectedDate;
        this.deliveryDate = deliveryDate;
        this.payment = payment;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRiderID() {
        return riderID;
    }

    public void setRiderID(String riderID) {
        this.riderID = riderID;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
