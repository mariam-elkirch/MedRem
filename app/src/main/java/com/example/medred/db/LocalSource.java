package com.example.medred.db;


import androidx.lifecycle.LiveData;

import com.example.medred.model.Medication;

import java.util.List;

public interface LocalSource {
    void insertMedication(Medication medicationModel);
    void deleteMedication(Medication medicationModel);
    LiveData<List<Medication>> getAllMedications();
}
