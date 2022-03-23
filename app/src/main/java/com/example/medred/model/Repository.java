package com.example.medred.model;

import android.content.Context;
import android.util.Log;

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
    public void addHealthTaker(String healthTakerEmail) {
        firebaseSource.sendRequest(healthTakerEmail);
    }

    @Override
    public void getRequests() {
        firebaseSource.getRequests();
    }

    @Override
    public void acceptRequest(Request request) {
        firebaseSource.acceptRequest(request);
    }

    @Override
    public void rejectRequest(Request request) {
        firebaseSource.rejectRequest(request);
    }

    @Override
    public void getHealthTakers() {
        firebaseSource.getHealthTakers();
    }

    @Override
    public void getDependants() {
        firebaseSource.getDependants();
    }

    @Override
    public void deleteHealthTaker(HealthTaker healthTaker) {
        firebaseSource.deleteHealthTaker(healthTaker);
    }

    @Override
    public void deleteDependant(Dependant dependant) {
        firebaseSource.deleteDependant(dependant);
    }




    @Override
    public LiveData<List<Medication>> getCalenderMedications(long time) {
      return   localSource.getCalenderMedications(time);
    }



    @Override
    public LiveData<List<Medication>> getSpecificDayCalenderMedications(long time, String day) {
        return localSource.getSpecificDayCalenderMedications(time,day);
    }

    @Override
    public void getFirebaseMedications() {
        Log.i("TAG", "getmedication Repo");
        firebaseSource.getMedications();

    }

}
