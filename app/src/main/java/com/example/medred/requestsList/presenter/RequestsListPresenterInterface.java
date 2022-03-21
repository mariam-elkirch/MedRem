package com.example.medred.requestsList.presenter;

import com.example.medred.model.Request;

public interface RequestsListPresenterInterface {
    void getRequests();
    void acceptRequest(Request request);
    void rejectRequest(Request request);
}
