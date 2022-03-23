package com.example.medred.db;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.medred.model.Medication;

import java.util.List;

@Dao
public interface MedicationDAO {

    @Query("SELECT * from medication")
    LiveData<List<Medication>> getAllMedications();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMedication(Medication medicationModel);

    @Delete
    void deleteMedication(Medication medicationModel);


    @Query("SELECT * FROM medication WHERE (:time BETWEEN startDateInMillis AND endDateInMillis) AND isActive = 1")
    LiveData<List<Medication>> getActiveMedications(long time);

    @Query("SELECT * FROM medication WHERE (:time < startDateInMillis OR :time > endDateInMillis) OR isActive = 0")
    LiveData<List<Medication>> getInactiveMedications(long time);

    @Query("SELECT * FROM medication WHERE (:time BETWEEN startDateInMillis AND endDateInMillis)  AND isActive = 1")
    LiveData<List<Medication>> getCalenderMedications(long time);

   /* @Query("SELECT * FROM medication WHERE (:time BETWEEN startDateInMillis AND endDateInMillis) AND days LIKE:day  AND frequency=3 AND isActive = 1")
    LiveData<List<Medication>> getSpecificDayCalenderMedications(long time,String day);*/

    @Query("SELECT * FROM medication WHERE ((:time BETWEEN startDateInMillis AND endDateInMillis) AND isActive = 1) AND((frequency=2)OR(frequency=3 AND days LIKE :day))")
    LiveData<List<Medication>> getSpecificDayCalenderMedications(long time,String day);

    //where medication name pillstock -1 in case take
}
