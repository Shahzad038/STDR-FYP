package com.student.smartETailor.models;

public class Order {
    private String orderID, name, price, status;
    private Measurement measurement;
    private String note;
    private String tailorID, designID;

    private Ship ship;

    private Review reviewProduct, reviewRider, reviewTailor;

    public Order() {
        orderID = "";
        name = "";
        price = "";
        status = "";
        measurement = new Measurement();
        note = "";
        tailorID = "";
        designID = "";
        ship = new Ship();
        reviewProduct = new Review();
        reviewRider = new Review();
        reviewTailor = new Review();
    }

    public Order(String orderID, String name, String price, String status, Measurement measurement, String note,
                 String tailorID, String designID, Ship ship, Review reviewProduct, Review reviewRider, Review reviewTailor) {
        this.orderID = orderID;
        this.name = name;
        this.price = price;
        this.status = status;
        this.measurement = measurement;
        this.note = note;
        this.tailorID = tailorID;
        this.designID = designID;
        this.ship = ship;
        this.reviewProduct = reviewProduct;
        this.reviewRider = reviewRider;
        this.reviewTailor = reviewTailor;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTailorID() {
        return tailorID;
    }

    public void setTailorID(String tailorID) {
        this.tailorID = tailorID;
    }

    public String getDesignID() {
        return designID;
    }

    public void setDesignID(String designID) {
        this.designID = designID;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Review getReviewProduct() {
        return reviewProduct;
    }

    public void setReviewProduct(Review reviewProduct) {
        this.reviewProduct = reviewProduct;
    }

    public Review getReviewRider() {
        return reviewRider;
    }

    public void setReviewRider(Review reviewRider) {
        this.reviewRider = reviewRider;
    }

    public Review getReviewTailor() {
        return reviewTailor;
    }

    public void setReviewTailor(Review reviewTailor) {
        this.reviewTailor = reviewTailor;
    }
}
