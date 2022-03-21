package com.example.medred.model;

import java.util.ArrayList;

public class Reminders {
    private String name;
    private int numberOfDoses;
    private ArrayList<Alarm> setAlarm;
    private int imageID;
    private String strength;
    private String unit;
    private  int id;
    private  long timeImMills;
    public Reminders(String name, int numberOfDoses, ArrayList<Alarm> setAlarm, int imageID, String strength, String unit, int id,long timeImMills) {
        this.name = name;
        this.numberOfDoses = numberOfDoses;
        this.setAlarm = setAlarm;
        this.imageID = imageID;
        this.strength = strength;
        this.unit = unit;
        this.id = id;
        this.timeImMills=timeImMills;
    }

    public long getTimeImMills() {
        return timeImMills;
    }

    public void setTimeImMills(long timeImMills) {
        this.timeImMills = timeImMills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfDoses() {
        return numberOfDoses;
    }

    public void setNumberOfDoses(int numberOfDoses) {
        this.numberOfDoses = numberOfDoses;
    }

    public ArrayList<Alarm> getSetAlarm() {
        return setAlarm;
    }

    public void setSetAlarm(ArrayList<Alarm> setAlarm) {
        this.setAlarm = setAlarm;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
