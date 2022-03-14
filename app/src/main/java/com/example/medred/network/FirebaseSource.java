package com.example.medred.network;

import com.example.medred.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface FirebaseSource {
void authunticateUser(User user, FirebaseManager.FireBaseCallBack fireBaseCallBack);
void RegisterationGoogle(GoogleSignInAccount account,FirebaseManager.FireBaseCallBack fireBaseCallBack);
void loginEmailPassword(String email,String password,FirebaseManager.FireBaseCallBack fireBaseCallBack);
void  forgotPassword(String email);
}
