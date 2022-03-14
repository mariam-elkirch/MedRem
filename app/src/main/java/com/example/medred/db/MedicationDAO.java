package com.example.medred.db;


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
}
