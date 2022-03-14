package com.example.medred.medicationsList.presenter;

import androidx.lifecycle.LifecycleOwner;

public interface MedicationsListIPresenterInterface {
    void getActiveMedications(LifecycleOwner owner);
    void getInactiveMedications(LifecycleOwner owner);
}
