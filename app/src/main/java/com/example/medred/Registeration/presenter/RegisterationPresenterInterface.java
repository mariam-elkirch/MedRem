package com.example.medred.Registeration.presenter;

import com.example.medred.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface RegisterationPresenterInterface {
    void createAccount(User user);
    void RegistrationGoogle(GoogleSignInAccount account);
    void LoginGoogle(GoogleSignInAccount account);
    void loginEmailPassword(String email,String password);
    void forgotPassword(String email);
}
