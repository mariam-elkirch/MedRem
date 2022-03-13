package com.example.medred.network;

import com.example.medred.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface FirebaseSource {
Boolean authunticateUser(User user, FirebaseManager.FireBaseCallBack fireBaseCallBack);
Boolean RegisterationGoogle(GoogleSignInAccount account,FirebaseManager.FireBaseCallBack fireBaseCallBack);
}
