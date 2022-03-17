package com.example.medred.medicationsList.view;

import com.example.medred.model.Medication;

public interface OnMedicationClickListener {
    void onClick(int medicationId);
    void onEdit(int medicationId);
    void onDelete(Medication medication);
}
