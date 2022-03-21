package com.example.medred.requestsList.view;

import com.example.medred.model.Request;

public interface OnRequestClickListener {
    void onAcceptRequest(Request request);
    void onRejectRequest(Request request);
}
