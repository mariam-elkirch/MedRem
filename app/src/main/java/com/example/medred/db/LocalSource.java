package com.example.medred.db;


import androidx.lifecycle.LiveData;

import com.example.medred.model.Alarm;
import com.example.medred.model.Medication;

import java.util.List;

public interface LocalSource {
    void insertMedication(Medication medicationModel);
    void deleteMedication(Medication medicationModel);
    LiveData<List<Medication>> getAllMedications();
    LiveData<List<Medication>> getActiveMedications(long time);
    LiveData<List<Medication>> getInactiveMedications(long time);
    LiveData<Medication> getShowMedication(int medID);
    void updateMedication(Medication medicationModel);
    void takeMedication (String pillStock,int Id);
    void rescheduleMedication (Alarm alarm, int Id);
}
