package com.example.medred.addmedication.view;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.medred.model.Medication;

import java.util.List;

public interface AddMedicationViewInterface {

    void setMedicationView(Medication medicationModel);
    //void getShowMed(Medication medicationModel);
    LiveData<Medication> getShowMed (int idMed);
    void updateMedication(Medication medicationModel);

}
