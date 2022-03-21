package com.example.medred.dependentsList.presenter;

import com.example.medred.dependentsList.view.DependantsListViewInterface;
import com.example.medred.model.Dependant;
import com.example.medred.model.HealthTaker;
import com.example.medred.model.Medication;
import com.example.medred.model.RepositoryInterface;
import com.example.medred.model.Request;
import com.example.medred.network.NetworkDelegate;

import java.util.ArrayList;
import java.util.List;

public class DependantsListPresenter implements DependantsListPresenterInterface, NetworkDelegate {

    DependantsListViewInterface dependantsView;
    RepositoryInterface repo;

    public DependantsListPresenter(DependantsListViewInterface dependantsView, RepositoryInterface repo){
        this.dependantsView = dependantsView;
        this.repo = repo;
    }

    @Override
    public void getDependants() {
        repo.setNetworkDelegate(this);
        repo.getDependants();
    }

    @Override
    public void onSuccessGettingDependants(List<Dependant> dependants) {
        dependantsView.showDependants(dependants);
    }

    @Override
    public void deleteDependant(Dependant dependant) {
        //repo.setNetworkDelegate(this);
        repo.deleteDependant(dependant);
    }

    @Override
    public void onDeletingDependant(boolean isDeleted) {
        //repo.setNetworkDelegate(null);
        dependantsView.onDeletingDependant(isDeleted);
    }

    @Override
    public void onDeletingHealthTaker(boolean isDeleted) {

    }

    @Override
    public void switchToDependant(String dependantEmail) {

    }

    @Override
    public void onSuccessResult(ArrayList<Medication> medicationModel) {

    }

    @Override
    public void onFailureResult(String errorMsg) {

    }

    @Override
    public void isUserExist(boolean userExistence, String receiverId) {

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

    @Override
    public void onSuccessGettingHealthTakers(List<HealthTaker> healthTakers) {

    }
}
