package com.example.medred.requestsList.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.example.medred.model.Dependant;
import com.example.medred.model.HealthTaker;
import com.example.medred.model.Medication;
import com.example.medred.model.RepositoryInterface;
import com.example.medred.model.Request;
import com.example.medred.network.NetworkDelegate;
import com.example.medred.requestsList.view.RequestsListViewInterface;

import java.util.ArrayList;
import java.util.List;

public class RequestsListPresenter implements RequestsListPresenterInterface, NetworkDelegate {

    //TODO: Complete requests list presenter

    private RequestsListViewInterface reqView;
    private RepositoryInterface repo;

    public RequestsListPresenter(RequestsListViewInterface reqView, RepositoryInterface repo){
        this.reqView = reqView;
        this.repo = repo;
    }

    @Override
    public void getRequests() {
        repo.setNetworkDelegate(this);
        repo.getRequests();
    }

    @Override
    public void onSuccessRequests(List<Request> requests) {
        reqView.getRequests(requests);
    }

    @Override
    public void onFailureRequests(String errorMsg) {

    }

    @Override
    public void acceptRequest(Request request) {
//        repo.setNetworkDelegate(this);
        repo.acceptRequest(request);
    }

    @Override
    public void rejectRequest(Request request) {
//        repo.setNetworkDelegate(this);
        repo.rejectRequest(request);
    }

    @Override
    public void onSuccessAcceptingRequest(Boolean isAccepted) {
        reqView.onSuccessAcceptingRequest(isAccepted);
    }

    @Override
    public void onSuccessRejectingRequest(Boolean isRejected) {
        reqView.onSuccessRejectingRequest(isRejected);
    }

    @Override
    public void onSuccessGettingHealthTakers(List<HealthTaker> healthTakers) {

    }

    @Override
    public void onSuccessGettingDependants(List<Dependant> dependants) {

    }

    @Override
    public void onDeletingHealthTaker(boolean isDeleted) {

    }

    @Override
    public void onDeletingDependant(boolean isDeleted) {

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
}
