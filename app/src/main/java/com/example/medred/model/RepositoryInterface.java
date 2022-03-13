package com.example.medred.model;

import com.example.medred.network.FirebaseManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface RepositoryInterface {
Boolean authunticateUser(User user, FirebaseManager.FireBaseCallBack fireBaseCallBack);
 Boolean RegisterationGoogle(GoogleSignInAccount account, FirebaseManager.FireBaseCallBack fireBaseCallBack);
}
