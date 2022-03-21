package com.example.medred.Registeration.presenter;

import android.util.Log;

import com.example.medred.Registeration.view.ForgotPasswordViewInterface;
import com.example.medred.Registeration.view.LoginViewInterface;
import com.example.medred.Registeration.view.RegisterationViewInterface;
import com.example.medred.model.Repository;
import com.example.medred.model.RepositoryInterface;
import com.example.medred.model.User;
import com.example.medred.network.FirebaseManager;
import com.example.medred.network.FirebaseSource;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class RegistrationPresenter implements RegisterationPresenterInterface  {
    RepositoryInterface repo;
    RegisterationViewInterface view;
    LoginViewInterface loginView;
    ForgotPasswordViewInterface forgotPasswordViewInterface;
  boolean result;
    public RegistrationPresenter(RepositoryInterface repo, RegisterationViewInterface view) {
        this.repo = repo;
        this.view = view;
    }
    public RegistrationPresenter(RepositoryInterface repo, LoginViewInterface loginView) {
        this.repo = repo;
        this.loginView = loginView;
    }
    public RegistrationPresenter(RepositoryInterface repo, ForgotPasswordViewInterface forgotPasswordViewInterface) {
        this.repo = repo;
        this.forgotPasswordViewInterface = forgotPasswordViewInterface;
    }
    @Override
    public void createAccount(User user) {
      repo.authunticateUser(user, stored -> {
          Log.i("TAG",result+"Result");
          view.GoToHome(stored);
      });


                // Boolean success=repo.authunticateUser(user);
                //view.GoToHome(success);
    }

    @Override
    public void RegistrationGoogle(GoogleSignInAccount account) {
       repo.RegisterationGoogle(account, new FirebaseManager.FireBaseCallBack() {
            @Override
            public void onCallBack(boolean stored) {
                Log.i("TAG",result+"Result");
                view.GoToHome(stored);
            }
        });
    }

    @Override
    public void LoginGoogle(GoogleSignInAccount account) {
       repo.RegisterationGoogle(account, new FirebaseManager.FireBaseCallBack() {
            @Override
            public void onCallBack(boolean stored) {
                Log.i("TAG",result+"Result");
                loginView.GoToHome(stored);
            }
        });
    }

    @Override
    public void loginEmailPassword(String email, String password) {
            repo.loginEmailPassword(email,password, new FirebaseManager.FireBaseCallBack() {
                @Override
                public void onCallBack(boolean stored) {
                   loginView.GoToHome(stored);
                }
            });
    }

    @Override
    public void forgotPassword(String email) {
      repo.forgotPassword(email);
    }


}
