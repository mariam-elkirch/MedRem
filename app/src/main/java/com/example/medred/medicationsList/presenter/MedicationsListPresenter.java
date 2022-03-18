package com.example.medred.medicationsList.presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.medred.medicationsList.view.MedicationsListViewInterface;
import com.example.medred.model.Medication;
import com.example.medred.model.RepositoryInterface;

import java.util.Calendar;
import java.util.List;

public class MedicationsListPresenter implements MedicationsListIPresenterInterface{

    //TODO: Complete medications list presenter

    private MedicationsListViewInterface medView;
    private RepositoryInterface repo;
    private long time = Calendar.getInstance().getTimeInMillis();

    public MedicationsListPresenter(MedicationsListViewInterface medView, RepositoryInterface repo){
        this.medView = medView;
        this.repo = repo;
    }


    @Override
    public void getActiveMedications(LifecycleOwner owner) {
        Log.d("TAG", time+"");
        repo.getActiveMedications(time).observe(owner, medications -> medView.getActiveMeds(medications));
    }

    @Override
    public void getInactiveMedications(LifecycleOwner owner) {
        repo.getInactiveMedications(time).observe(owner, medications -> medView.getInactiveMeds(medications));
    }

    @Override
    public void deleteMedication(Medication medication) {
        repo.deleteMedication(medication);
    }
}
