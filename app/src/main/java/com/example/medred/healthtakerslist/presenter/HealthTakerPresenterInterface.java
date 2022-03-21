package com.example.medred.healthtakerslist.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.example.medred.model.HealthTaker;
import com.example.medred.model.Request;

public interface HealthTakerPresenterInterface {
    void getHealthTakers();
    void deleteHealthTaker(HealthTaker healthTaker);
    void addHealthTaker(String healthTakerEmail);
    void userExistence(String receiverEmail);
}
