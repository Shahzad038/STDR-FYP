package com.student.smartETailor.models;

public class Review {
    private String note;
    private float stars;


    public Review() {
    }

    public Review(String note, float stars) {
        this.note = note;
        this.stars = stars;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }
}
