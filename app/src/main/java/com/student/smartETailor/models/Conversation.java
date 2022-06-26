package com.student.smartETailor.models;

import java.io.Serializable;

public class Conversation implements Serializable {
    private String ucid, name, uid, lastSms;
    private int read;

    public Conversation() {
        this.ucid = "";
        this.name = "";
        this.uid = "";
        this.lastSms = "";
        read = 0;
    }

    public Conversation(String ucid, String name, String uid, String lastSms, int read) {
        this.ucid = ucid;
        this.name = name;
        this.uid = uid;
        this.lastSms = lastSms;
        this.read = read;
    }

    public String getLastSms() {
        return lastSms;
    }

    public void setLastSms(String lastSms) {
        this.lastSms = lastSms;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public String getUcid() {
        return ucid;
    }

    public void setUcid(String ucid) {
        this.ucid = ucid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}