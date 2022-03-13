package com.example.medred.model;

import androidx.lifecycle.LiveData;

import com.example.medred.db.LocalSource;

import java.util.List;

public interface MedicationRepositoryInterface {

    LiveData<List<Medication>> getAllMedications();
    void getAllMedication(LocalSource localSource);
    void insertMedication(Medication medicationModel);
    void deleteMedication(Medication medicationModel);
}
