package com.brownie.mynotesapp.model;

import java.sql.Time;

public class Note {

    private String rDescription;

    private String rTime;

    public Note() {
    }

    public Note(String rDescription, String rTime) {
        this.rDescription = rDescription;
        this.rTime = rTime;
    }

    public String getrDescription() {
        return rDescription;
    }

    public void setrDescription(String rDescription) {
        this.rDescription = rDescription;
    }

    public String getrTime() {
        return rTime;
    }

    public void setrTime(String rTime) {
        this.rTime = rTime;
    }

    @Override
    public String toString() {
        return "Note{" +
                "rDescription='" + rDescription + '\'' +
                ", rTime='" + rTime + '\'' +
                '}';
    }
}
