package com.student.smartETailor.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Design  implements Serializable {
    private String designID, tailorID, name, description;
    private ArrayList<Measurement> measurements;
    private boolean customization;
    private String price;
    private String culture;

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    private ArrayList<String> designs;

    private ArrayList<String> orders;

    public Design() {
        measurements = new ArrayList<>();
        designs = new ArrayList<>();
        orders = new ArrayList<>();
        customization = false;
    }

    public Design(String designID, String tailorID, String name, String description,
                  ArrayList<Measurement> measurements, Boolean customization,String price, ArrayList<String> designs) {
        this.designID = designID;
        this.tailorID = tailorID;
        this.price = price;
        this.customization = customization;
        this.name = name;
        this.description = description;
        this.measurements = measurements;
        this.designs = designs;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isCustomization() {
        return customization;
    }

    public void setCustomization(boolean customization) {
        this.customization = customization;
    }

    public ArrayList<String> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<String> orders) {
        this.orders = orders;
    }

    public String getDesignID() {
        return designID;
    }

    public void setDesignID(String designID) {
        this.designID = designID;
    }

    public String getTailorID() {
        return tailorID;
    }

    public void setTailorID(String tailorID) {
        this.tailorID = tailorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(ArrayList<Measurement> measurements) {
        this.measurements = measurements;
    }

    public ArrayList<String> getDesigns() {
        return designs;
    }

    public void setDesigns(ArrayList<String> designs) {
        this.designs = designs;
    }

    public void addDesign(String designURL) {
        this.designs.add(designURL);
    }
}
