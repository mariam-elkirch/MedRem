package com.example.medred.medicationsList.view;

import com.example.medred.model.Medication;

import java.util.List;

public interface MedicationsListViewInterface {
     void getActiveMeds(List<Medication> activeMedications);
     void getInactiveMeds(List<Medication> inactiveMedications);
}
