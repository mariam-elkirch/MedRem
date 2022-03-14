package com.example.medred.medicationsList.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.example.medred.medicationsList.view.MedicationsListViewInterface;

public class MedicationsListPresenter implements MedicationsListIPresenterInterface{

    //TODO: Complete medications list presenter

    private MedicationsListViewInterface medView;

    public MedicationsListPresenter(MedicationsListViewInterface medView){
        this.medView = medView;
    }


    @Override
    public void getActiveMedications(LifecycleOwner owner) {

    }

    @Override
    public void getInactiveMedications(LifecycleOwner owner) {

    }
}
