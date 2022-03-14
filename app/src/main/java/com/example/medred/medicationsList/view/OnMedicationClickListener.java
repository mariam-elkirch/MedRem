package com.example.medred.medicationsList.view;

public interface OnMedicationClickListener {
    void onClick(String medicationId);
    void onEdit(String medicationId);
    void onDelete(String medicationId);
}
