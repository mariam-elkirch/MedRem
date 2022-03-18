package com.example.medred.requestsList.presenter;

import androidx.lifecycle.LifecycleOwner;

public interface RequestsListPresenterInterface {

    void getRequests(LifecycleOwner owner);
    void refuseRequest(String senderEmail);
    void acceptRequest(String senderEmail);
}
