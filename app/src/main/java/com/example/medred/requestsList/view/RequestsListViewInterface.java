package com.example.medred.requestsList.view;

import com.example.medred.model.Request;
import java.util.List;

public interface RequestsListViewInterface {
    void getRequests(List<Request> requests);
    void onSuccessAcceptingRequest(Boolean isAccepted);
    void onSuccessRejectingRequest(Boolean isRejected);
}
