package com.student.smartETailor.models;


public class MySMS {
    private String body, time, send, receive, type;

    public MySMS() {
        this.type = "";
        this.body = "";
        this.time = "";
        this.send = "";
        this.receive = "";
    }

    public MySMS(String body, String time, String send, String receive, String type) {
        this.body = body;
        this.time = time;
        this.send = send;
        this.receive = receive;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }
}