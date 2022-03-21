package com.example.medred.Home.presenter;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.medred.model.Medication;
import com.example.medred.model.Reminders;

import java.util.List;

public interface HomePresenterInterface {
    void getCalenderMedications(Long time,LifecycleOwner owner);
    List<Reminders> getReminders(List<Medication>reminders);

}
