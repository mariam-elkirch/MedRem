package com.example.medred.Home.presenter;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.medred.model.HealthTaker;
import com.example.medred.model.Medication;
import com.example.medred.model.Reminders;

import java.util.List;

public interface HomePresenterInterface {
    void getCalenderMedications(Long time,LifecycleOwner owner,String day);
    List<Reminders> getReminders(List<Medication>reminders);
    public void getFirebaseMedications(long time,LifecycleOwner owner,String day);
}
