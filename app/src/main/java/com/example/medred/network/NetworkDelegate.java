package com.example.medred.network;


import com.example.medred.model.Medication;

import java.util.ArrayList;

public interface NetworkDelegate {
    void onSuccessResult(ArrayList<Medication> medicationModel);
    void onFailureResult(String errorMsg);
    void isUserExist(boolean userExistence, String receiverId);
}
