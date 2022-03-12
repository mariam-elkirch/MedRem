package com.example.medred.model;

import java.util.ArrayList;
public class Medication {
    private  String id;
    private String name;
    private int strength;
    private String unit;
    private int frequency;
    private int imageID;

    private String numberOfDoses;
    private String startDate;
    private String endDate;
    private ArrayList<String> days;

    private ArrayList<String> setAlarm;
    private ArrayList<String> pillEachDose;

    private String reason;
    private String pillStock;
    private boolean refillReminder;
    private String leftPillReminder;
    private String alarmRefillTime;

    public Medication() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getNumberOfDoses() {
        return numberOfDoses;
    }

    public void setNumberOfDoses(String numberOfDoses) {
        this.numberOfDoses = numberOfDoses;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public ArrayList<String> getSetAlarm() {
        return setAlarm;
    }

    public void setSetAlarm(ArrayList<String> setAlarm) {
        this.setAlarm = setAlarm;
    }

    public ArrayList<String> getPillEachDose() {
        return pillEachDose;
    }

    public void setPillEachDose(ArrayList<String> pillEachDose) {
        this.pillEachDose = pillEachDose;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPillStock() {
        return pillStock;
    }

    public void setPillStock(String pillStock) {
        this.pillStock = pillStock;
    }

    public boolean isRefillReminder() {
        return refillReminder;
    }

    public void setRefillReminder(boolean refillReminder) {
        this.refillReminder = refillReminder;
    }

    public String getLeftPillReminder() {
        return leftPillReminder;
    }

    public void setLeftPillReminder(String leftPillReminder) {
        this.leftPillReminder = leftPillReminder;
    }

    public String getAlarmRefillTime() {
        return alarmRefillTime;
    }

    public void setAlarmRefillTime(String alarmRefillTime) {
        this.alarmRefillTime = alarmRefillTime;
    }

    public Medication(String id, String name, int strength, String unit, int frequency, int imageID, String numberOfDoses, String startDate, String endDate, ArrayList<String> days, ArrayList<String> setAlarm, ArrayList<String> pillEachDose, String reason, String pillStock, boolean refillReminder, String leftPillReminder, String alarmRefillTime) {
        this.id = id;
        this.name = name;
        this.strength = strength;
        this.unit = unit;
        this.frequency = frequency;
        this.imageID = imageID;
        this.numberOfDoses = numberOfDoses;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.setAlarm = setAlarm;
        this.pillEachDose = pillEachDose;
        this.reason = reason;
        this.pillStock = pillStock;
        this.refillReminder = refillReminder;
        this.leftPillReminder = leftPillReminder;
        this.alarmRefillTime = alarmRefillTime;
    }
}
