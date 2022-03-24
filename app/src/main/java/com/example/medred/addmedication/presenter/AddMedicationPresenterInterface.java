package com.example.medred.addmedication.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.medred.model.Alarm;
import com.example.medred.model.Medication;

import java.util.ArrayList;
import java.util.List;

public interface AddMedicationPresenterInterface {

    void setMedicationPresenter(Medication medicationModel);

    LiveData<Medication> getShowMed (int idMed);
    void updateMedication(Medication medicationModel);
    void takeMedication (String pillStock,String name);
    void rescheduleMedication (ArrayList<Alarm> alarm, String name);
    LiveData<Medication> getPill(String name);

    void getIdFromShow (int id);


}
