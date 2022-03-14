package com.example.medred.healthtakerslist.presenter;

import androidx.lifecycle.LifecycleOwner;
import com.example.medred.healthtakerslist.view.HealthTakersListViewInterface;

public class HealthTakerPresenter implements HealthTakerPresenterInterface{

    HealthTakersListViewInterface healthTakerView;

    public HealthTakerPresenter(HealthTakersListViewInterface healthTakerView) {
        this.healthTakerView = healthTakerView;
    }

    @Override
    public void getHealthTakers(LifecycleOwner owner) {

    }

    @Override
    public void deleteHealthTaker(String healthTakerEmail) {

    }

    @Override
    public void addHealthTaker(String healthTakerEmail) {

    }
}
