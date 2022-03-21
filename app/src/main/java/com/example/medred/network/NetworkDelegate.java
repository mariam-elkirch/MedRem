package com.example.medred.network;

import com.example.medred.model.Dependant;
import com.example.medred.model.HealthTaker;
import com.example.medred.model.Medication;
import com.example.medred.model.Request;
import java.util.ArrayList;
import java.util.List;

public interface NetworkDelegate {
    void onSuccessResult(ArrayList<Medication> medicationModel);
    void onFailureResult(String errorMsg);
    void isUserExist(boolean userExistence, String receiverId);
    void onSuccessRequests(List<Request> requests);
    void onFailureRequests(String errorMsg);
    void onSuccessAcceptingRequest(Boolean isAccepted);
    void onSuccessRejectingRequest(Boolean isRejected);
    void onSuccessGettingHealthTakers(List<HealthTaker> healthTakers);
    void onSuccessGettingDependants(List<Dependant> dependants);
    void onDeletingHealthTaker(boolean isDeleted);
    void onDeletingDependant(boolean isDeleted);
}
