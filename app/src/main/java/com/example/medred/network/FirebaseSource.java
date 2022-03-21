package com.example.medred.network;

import com.example.medred.model.Dependant;
import com.example.medred.model.HealthTaker;
import com.example.medred.model.Request;
import com.example.medred.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface FirebaseSource {
    void authunticateUser(User user, FirebaseManager.FireBaseCallBack fireBaseCallBack);
    void RegisterationGoogle(GoogleSignInAccount account,FirebaseManager.FireBaseCallBack fireBaseCallBack);
    void loginEmailPassword(String email,String password,FirebaseManager.FireBaseCallBack fireBaseCallBack);
    void forgotPassword(String email);
    void userExistence(String email);
    void sendRequest(String receiverId);
    void acceptRequest(Request request);
    void rejectRequest(Request request);
    void getRequests();
    void setNetworkDelegate(NetworkDelegate networkDelegate);
    void getHealthTakers();
    void getDependants();
    void deleteHealthTaker(HealthTaker healthTaker);
    void deleteDependant(Dependant dependant);
}
