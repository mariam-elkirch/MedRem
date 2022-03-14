package com.example.medred.requestsList.view;

public interface OnRequestClickListener {
    void onAcceptRequest(String senderEmail);
    void onRefuseRequest(String senderEmail);
}
