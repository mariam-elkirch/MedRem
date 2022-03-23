package com.example.medred.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.medred.db.LocalSource;
import com.example.medred.network.FirebaseManager;
import com.example.medred.network.FirebaseSource;
import com.example.medred.network.NetworkDelegate;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class Repository implements RepositoryInterface{
    private FirebaseSource firebaseSource;
    // RemoteSource remoteSource;
    private LocalSource localSource;
    private NetworkDelegate networkDelegate;

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
    public void setNetworkDelegate(NetworkDelegate networkDelegate) {
        this.networkDelegate = networkDelegate;
        firebaseSource.setNetworkDelegate(networkDelegate);
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
    public LiveData<List<Medication>> getActiveMedications(long time) {
        return localSource.getActiveMedications(time);
    }

    @Override
    public LiveData<List<Medication>> getInactiveMedications(long time) {
        return localSource.getInactiveMedications(time);
    }

    @Override
    public LiveData<Medication> getShowMedication(int medID) {
        return localSource.getShowMedication(medID);
    }

    @Override
    public void updateMedication(Medication medicationModel) {
        localSource.updateMedication(medicationModel);
    }

    @Override
    public void takeMedication(String pillStock, int Id) {
        localSource.takeMedication(pillStock,Id);
    }

    @Override
    public void rescheduleMedication(Alarm alarm, int Id) {
        localSource.rescheduleMedication(alarm,Id);
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

    @Override
    public void userExistence(String receiverEmail) {
        firebaseSource.userExistence(receiverEmail);
    }

    @Override
    public void addHealthTaker(Request request, String healthTakerEmail) {
        firebaseSource.sendRequest(request, healthTakerEmail);
    }

}
