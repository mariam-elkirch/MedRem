package com.example.medred.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.medred.db.LocalSource;
import com.example.medred.network.FirebaseManager;
import com.example.medred.network.FirebaseSource;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class Repository implements RepositoryInterface{
    FirebaseSource firebaseSource;
    // RemoteSource remoteSource;
    LocalSource localSource;


    Context context;
    Boolean success=false;
    private static Repository repo = null;
    public Repository(Context context, FirebaseSource firebaseSource, LocalSource localSource) {
        this.firebaseSource=firebaseSource;
        this.context=context;
        this.localSource=localSource;
    }
    public  static  Repository getInstance(Context context, FirebaseSource firebaseSource, LocalSource localSource){
        if(repo==null)
            repo=new Repository(context,firebaseSource,localSource);
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
    @Override
    public LiveData<List<Medication>> getAllMedications() {
        return localSource.getAllMedications();
    }

    @Override
    public void getAllMedication(LocalSource localSource) {

        localSource.getAllMedications();
    }

    @Override
    public void insertMedication(Medication medicationModel) {

        localSource.insertMedication(medicationModel);
    }

    @Override
    public void deleteMedication(Medication medicationModel) {

        localSource.deleteMedication(medicationModel);
    }

}
