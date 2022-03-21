package com.example.medred.Home.view;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.example.medred.model.Medication;

import java.util.List;

public interface HomeViewInterface {

    void getCalenderMeds(List<Medication> calenderMedications);
    void getFirebaseCalenderMeds(List<Medication> calenderMedications);

}
