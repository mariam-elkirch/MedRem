package com.example.medred.requestsList.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.example.medred.requestsList.view.RequestsListViewInterface;

public class RequestsListPresenter implements RequestsListPresenterInterface{

    //TODO: Complete requests list presenter

    private RequestsListViewInterface reqView;

    public RequestsListPresenter(RequestsListViewInterface reqView){
        this.reqView = reqView;
    }


    @Override
    public void getRequests(LifecycleOwner owner) {

    }

    @Override
    public void refuseRequest(String senderEmail) {

    }

    @Override
    public void acceptRequest(String senderEmail) {

    }
}
