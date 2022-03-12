package com.example.medred.model;

public class Alarm {
    private int hour;
    private int minute;
    private String format;

    public Alarm(int hour, int minute, String shift) {
        this.hour = hour;
        this.minute = minute;
        this.format = shift;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
