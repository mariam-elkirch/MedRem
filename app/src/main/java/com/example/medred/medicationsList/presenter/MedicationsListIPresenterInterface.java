package com.example.medred.medicationsList.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.example.medred.model.Medication;

public interface MedicationsListIPresenterInterface {
    void getActiveMedications(LifecycleOwner owner);
    void getInactiveMedications(LifecycleOwner owner);
    void deleteMedication(Medication medication);
}
