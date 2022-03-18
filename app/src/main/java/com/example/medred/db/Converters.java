package com.example.medred.db;


import androidx.room.TypeConverter;

import com.example.medred.model.Alarm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public String fromAlarmToString(Alarm alarm){
        return new Gson().toJson(alarm);
    }

    @TypeConverter
    public Alarm fromStringToAlarm(String stringAlarm){
        return new Gson().fromJson(stringAlarm,Alarm.class);
    }

    //try
    @TypeConverter
    public static ArrayList<Alarm> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Alarm>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Alarm> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    //another try
    @TypeConverter
    public static ArrayList<String> fromStringNum(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListNum(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
