package com.example.medred.db;


import androidx.lifecycle.LiveData;

import com.example.medred.model.Alarm;
import com.example.medred.model.Medication;

import java.util.ArrayList;
import java.util.List;

public interface LocalSource {
    void insertMedication(Medication medicationModel);
    void deleteMedication(Medication medicationModel);
    LiveData<List<Medication>> getAllMedications();
    LiveData<List<Medication>> getActiveMedications(long time);
    LiveData<List<Medication>> getInactiveMedications(long time);
    LiveData<List<Medication>> getCalenderMedications(long time);
    LiveData<List<Medication>> getSpecificDayCalenderMedications(long time,String day);
    LiveData<Medication> getShowMedication(int medID);
    void updateMedication(Medication medicationModel);
    void takeMedication (String pillStock,String name);
    void rescheduleMedication (ArrayList<Alarm> alarm, String name);
    LiveData<Medication> getPill(String name);
}
