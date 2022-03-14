package com.example.medred.model;

import com.example.medred.network.FirebaseManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface RepositoryInterface {
void authunticateUser(User user, FirebaseManager.FireBaseCallBack fireBaseCallBack);
 void RegisterationGoogle(GoogleSignInAccount account, FirebaseManager.FireBaseCallBack fireBaseCallBack);
 void loginEmailPassword(String email,String  password,FirebaseManager.FireBaseCallBack fireBaseCallBack);
 void forgotPassword(String  email);

}
