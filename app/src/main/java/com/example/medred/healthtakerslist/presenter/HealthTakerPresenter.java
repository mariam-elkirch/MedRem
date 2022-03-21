package com.example.medred.healthtakerslist.presenter;

import com.example.medred.healthtakerslist.view.HealthTakersListViewInterface;
import com.example.medred.model.Dependant;
import com.example.medred.model.HealthTaker;
import com.example.medred.model.Medication;
import com.example.medred.model.RepositoryInterface;
import com.example.medred.model.Request;
import com.example.medred.network.NetworkDelegate;
import java.util.ArrayList;
import java.util.List;

public class HealthTakerPresenter implements HealthTakerPresenterInterface, NetworkDelegate {

    HealthTakersListViewInterface healthTakerView;
    RepositoryInterface repo;

    public HealthTakerPresenter(HealthTakersListViewInterface healthTakerView, RepositoryInterface repo) {
        this.healthTakerView = healthTakerView;
        this.repo = repo;
    }

    @Override
    public void getHealthTakers() {
        repo.setNetworkDelegate(this);
        repo.getHealthTakers();
    }

    @Override
    public void addHealthTaker(String healthTakerEmail) {
        //repo.setNetworkDelegate(this);
        repo.addHealthTaker(healthTakerEmail);
    }

    @Override
    public void userExistence(String receiverEmail) {
        //repo.setNetworkDelegate(this);
        repo.userExistence(receiverEmail);
    }

    @Override
    public void isUserExist(boolean userExistence, String receiverId) {
        //repo.setNetworkDelegate(null);
        healthTakerView.isUserExist(userExistence, receiverId);
    }

    @Override
    public void onSuccessGettingHealthTakers(List<HealthTaker> healthTakers) {
        healthTakerView.showHealthTakers(healthTakers);
    }

    @Override
    public void deleteHealthTaker(HealthTaker healthTaker) {
        //repo.setNetworkDelegate(this);
        repo.deleteHealthTaker(healthTaker);
    }

    @Override
    public void onDeletingHealthTaker(boolean isDeleted) {
        //repo.setNetworkDelegate(null);
        healthTakerView.onDeletingHealthTaker(isDeleted);
    }

    @Override
    public void onDeletingDependant(boolean isDeleted) {

    }

    @Override
    public void onSuccessGettingDependants(List<Dependant> dependants) {

    }

    @Override
    public void onSuccessResult(ArrayList<Medication> medicationModel) {

    }

    @Override
    public void onFailureResult(String errorMsg) {

    }

    @Override
    public void onSuccessRequests(List<Request> requests) {

    }

    @Override
    public void onFailureRequests(String errorMsg) {

    }

    @Override
    public void onSuccessAcceptingRequest(Boolean isAccepted) {

    }

    @Override
    public void onSuccessRejectingRequest(Boolean isRejected) {

    }
}
