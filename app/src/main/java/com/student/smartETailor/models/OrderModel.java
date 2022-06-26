package com.student.smartETailor.models;

public class OrderModel {

    private static volatile OrderModel sSoleInstance = new OrderModel();
    private String customerId;
    private String tailorId;
    private Design design;
    private Measurement measurement;
    private String details;
    private String price;
    private String status; // pending and delivered
    private String orderId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String paymentMode;

    private OrderModel(){}

    public  void reset(){
          customerId = null;
          tailorId = null;
          design = null;
          measurement = null;
          details = null;
          price = null;
          status = null; // pending and delivered
          orderId = null;
          customerName = null;
          customerEmail = null;
          customerPhone = null;
          paymentMode = null;
    }


    public String getPaymentMode() {
        return paymentMode;
    }

    public static OrderModel getInstance() {
        return sSoleInstance;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTailorId() {
        return tailorId;
    }

    public void setTailorId(String tailorId) {
        this.tailorId = tailorId;
    }

    public Design getDesign() {
        return design;
    }

    public void setDesign(Design design) {
        this.design = design;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
