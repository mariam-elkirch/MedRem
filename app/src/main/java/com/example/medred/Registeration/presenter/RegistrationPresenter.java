package com.example.medred.Registeration.presenter;

import android.util.Log;

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
  boolean result;
    public RegistrationPresenter(RepositoryInterface repo, RegisterationViewInterface view) {
        this.repo = repo;
        this.view = view;
    }

    @Override
    public void createAccount(User user) {
       result=repo.authunticateUser(user, new FirebaseManager.FireBaseCallBack() {
           @Override
           public void onCallBack(boolean stored) {
               Log.i("TAG",result+"Result");
               view.GoToHome(stored);
           }
       });


                // Boolean success=repo.authunticateUser(user);
                //view.GoToHome(success);
    }

    @Override
    public void RegistrationGoogle(GoogleSignInAccount account) {
        result=repo.RegisterationGoogle(account, new FirebaseManager.FireBaseCallBack() {
            @Override
            public void onCallBack(boolean stored) {
                Log.i("TAG",result+"Result");
                view.GoToHome(stored);
            }
        });
    }


}
