package com.example.medred.Registeration.view;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface LoginViewInterface {
    void loginEmailPassword(String email,String password);
    void GoToHome(Boolean succes);
    void sendCredintalGoogle(GoogleSignInAccount account);
}
