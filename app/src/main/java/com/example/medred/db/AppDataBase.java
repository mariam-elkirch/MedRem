package com.example.medred.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.medred.model.Medication;

@Database(entities = {Medication.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase appDataBase = null;
    public abstract MedicationDAO medicationDAO();
    public static synchronized AppDataBase getInstance(Context context){
        if (appDataBase == null){
            appDataBase = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,"medications").build();
        }
        return appDataBase;
    }
}
