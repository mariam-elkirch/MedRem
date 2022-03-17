package com.example.medred.healthtakerslist.presenter;

import androidx.lifecycle.LifecycleOwner;
import com.example.medred.healthtakerslist.view.HealthTakersListViewInterface;
import com.example.medred.model.Medication;
import com.example.medred.model.RepositoryInterface;
import com.example.medred.model.Request;
import com.example.medred.network.NetworkDelegate;

import java.util.ArrayList;

public class HealthTakerPresenter implements HealthTakerPresenterInterface, NetworkDelegate {

    HealthTakersListViewInterface healthTakerView;
    RepositoryInterface repo;

    public HealthTakerPresenter(HealthTakersListViewInterface healthTakerView, RepositoryInterface repo) {
        this.healthTakerView = healthTakerView;
        this.repo = repo;
    }

    @Override
    public void getHealthTakers(LifecycleOwner owner) {

    }

    @Override
    public void deleteHealthTaker(String healthTakerEmail) {

    }

    @Override
    public void addHealthTaker(Request request, String healthTakerEmail) {
        repo.addHealthTaker(request, healthTakerEmail);
    }

    @Override
    public void userExistence(String receiverEmail) {
        repo.setNetworkDelegate(this);
        repo.userExistence(receiverEmail);
    }

    @Override
    public void onSuccessResult(ArrayList<Medication> medicationModel) {

    }

    @Override
    public void onFailureResult(String errorMsg) {

    }

    @Override
    public void isUserExist(boolean userExistence, String receiverId) {
        healthTakerView.isUserExist(userExistence, receiverId);
    }
}
