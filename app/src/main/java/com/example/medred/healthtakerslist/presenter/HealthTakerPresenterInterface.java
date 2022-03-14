package com.example.medred.healthtakerslist.presenter;

import androidx.lifecycle.LifecycleOwner;

public interface HealthTakerPresenterInterface {
    void getHealthTakers(LifecycleOwner owner);
    void deleteHealthTaker(String healthTakerEmail);
    void addHealthTaker(String healthTakerEmail);
}
