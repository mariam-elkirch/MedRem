package com.example.medred.Registeration.view;

import com.example.medred.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface RegisterationViewInterface {
void sendUserEmailPass(User user);
    void GoToHome(Boolean succes);
    void sendCredintalGoogle(GoogleSignInAccount account);
}
