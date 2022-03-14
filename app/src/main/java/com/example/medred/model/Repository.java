package com.example.medred.model;

import android.content.Context;

import com.example.medred.network.FirebaseManager;
import com.example.medred.network.FirebaseSource;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Repository implements RepositoryInterface{
    FirebaseSource firebaseSource;
   // RemoteSource remoteSource;

    Context context;
    Boolean success=false;
    private static Repository repo = null;
    public Repository(Context context, FirebaseSource firebaseSource) {
        this.firebaseSource=firebaseSource;
        this.context=context;
    }
    public  static  Repository getInstance(Context context, FirebaseSource firebaseSource){
        if(repo==null)
            repo=new Repository(context,firebaseSource);
        return  repo;
    }
    @Override
    public void authunticateUser(User user, FirebaseManager.FireBaseCallBack fireBaseCallBack) {
         // fireBaseCallBack.onCallBack(firebaseSource);
         firebaseSource.authunticateUser(user,fireBaseCallBack);
    }

    @Override
    public void RegisterationGoogle(GoogleSignInAccount account, FirebaseManager.FireBaseCallBack fireBaseCallBack) {
        firebaseSource.RegisterationGoogle(account,fireBaseCallBack);
    }

    @Override
    public void loginEmailPassword(String email, String password, FirebaseManager.FireBaseCallBack fireBaseCallBack) {
       firebaseSource.loginEmailPassword(email,password,fireBaseCallBack);
    }

    @Override
    public void forgotPassword(String email) {
      firebaseSource.forgotPassword(email);
    }


}
