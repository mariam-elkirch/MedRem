package com.example.medred.healthtakerslist.view;

import com.example.medred.model.HealthTaker;

public interface OnHealthTakerClickListener {
    void onRemoveHealthTaker(HealthTaker healthTaker);
    void onAddHealthTaker();
}
