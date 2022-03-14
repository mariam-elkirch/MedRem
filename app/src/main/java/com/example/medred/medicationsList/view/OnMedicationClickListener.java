package com.example.medred.medicationsList.view;

public interface OnMedicationClickListener {
    void onClick(int medicationId);
    void onEdit(int medicationId);
    void onDelete(int medicationId);
}
