package com.example.medred.healthtakerslist.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.example.medred.model.Request;

public interface HealthTakerPresenterInterface {
    void getHealthTakers(LifecycleOwner owner);
    void deleteHealthTaker(String healthTakerEmail);
    void addHealthTaker(Request request, String healthTakerEmail);
    void userExistence(String receiverEmail);
}
