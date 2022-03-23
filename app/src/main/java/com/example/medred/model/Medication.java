package com.example.medred.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.versionedparcelable.VersionedParcelize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "medication")
public class Medication implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private  int id;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "strength")
    @NonNull
    private String strength;

    @ColumnInfo(name = "unit")
    @NonNull
    private String unit;

    @ColumnInfo(name = "frequency")
    @NonNull
    private int frequency;

    @ColumnInfo(name = "imageID")
    @NonNull
    private int imageID;

    @ColumnInfo(name = "isActive")
    @NonNull
    private boolean isActive;

    @ColumnInfo(name = "numberOfDoses")

    private int numberOfDoses;

    @ColumnInfo(name = "startDate")

    private String startDate;

    @ColumnInfo(name = "endDate")

    private String endDate;

    @ColumnInfo(name = "startDateInMillis")

    private long startDateInMillis;

    @ColumnInfo(name = "endDateInMillis")

    private long endDateInMillis;

    @ColumnInfo(name = "days")

    private String days;

    @ColumnInfo(name = "setAlarm")

    private ArrayList<Alarm> setAlarm;

    @ColumnInfo(name = "pillEachDose")

    private ArrayList<String> pillEachDose;

    @ColumnInfo(name = "reason")
    private String reason;

    @ColumnInfo(name = "pillStock")

    private String pillStock;

    @ColumnInfo(name = "refillReminder")
    private boolean refillReminder;

    @ColumnInfo(name = "leftPillReminder")

    private String leftPillReminder;

    @ColumnInfo(name = "alarmRefillTime")

    private String alarmRefillTime;

    public Medication() {
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

    public int getNumberOfDoses() {
        return numberOfDoses;
    }

    public void setNumberOfDoses(int numberOfDoses) {
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public ArrayList<Alarm> getSetAlarm() {
        return setAlarm;
    }

    public void setSetAlarm(ArrayList<Alarm> setAlarm) {
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

    public Alarm getAlarmRefillTime() {
        return alarmRefillTime;
    }

    public void setAlarmRefillTime(Alarm alarmRefillTime) {
        this.alarmRefillTime = alarmRefillTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getStartDateInMillis() {
        return startDateInMillis;
    }

    public void setStartDateInMillis(long startDateInMillis) {
        this.startDateInMillis = startDateInMillis;
    }

    public long getEndDateInMillis() {
        return endDateInMillis;
    }

    public void setEndDateInMillis(long endDateInMillis) {
        this.endDateInMillis = endDateInMillis;
    }

    public Medication(String name, String strength, String unit, int frequency,
                      int imageID, int numberOfDoses, String startDate, String endDate, long startDateInMillis,
                      long endDateInMillis, String days, ArrayList<Alarm> setAlarm, ArrayList<String> pillEachDose,
                      String reason, String pillStock, boolean refillReminder, String leftPillReminder,
                      Alarm alarmRefillTime, boolean isActive) {
        //this.id = id;
        this.name = name;
        this.strength = strength;
        this.unit = unit;
        this.frequency = frequency;
        this.imageID = imageID;
        this.numberOfDoses = numberOfDoses;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startDateInMillis = startDateInMillis;
        this.endDateInMillis = endDateInMillis;
        this.days = days;
        this.setAlarm = setAlarm;
        this.pillEachDose = pillEachDose;
        this.reason = reason;
        this.pillStock = pillStock;
        this.refillReminder = refillReminder;
        this.leftPillReminder = leftPillReminder;
        this.alarmRefillTime = alarmRefillTime;
        this.isActive = isActive;
    }
}
