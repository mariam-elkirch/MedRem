package com.example.medred.addmedication.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.medred.model.Alarm;
import com.example.medred.model.Medication;

import java.util.List;

public interface AddMedicationPresenterInterface {

    void setMedicationPresenter(Medication medicationModel);

    LiveData<Medication> getShowMed (int idMed);
    void updateMedication(Medication medicationModel);
    void takeMedication (String pillStock,int Id);
    void rescheduleMedication (Alarm alarm, int Id);

    void getIdFromShow (int id);


}
