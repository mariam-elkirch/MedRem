package com.example.medred.model;

import androidx.lifecycle.LiveData;

import com.example.medred.db.LocalSource;
import com.example.medred.network.FirebaseManager;
import com.example.medred.network.NetworkDelegate;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public interface RepositoryInterface {
void authunticateUser(User user, FirebaseManager.FireBaseCallBack fireBaseCallBack);
 void RegisterationGoogle(GoogleSignInAccount account, FirebaseManager.FireBaseCallBack fireBaseCallBack);
 void loginEmailPassword(String email,String  password,FirebaseManager.FireBaseCallBack fireBaseCallBack);
 void forgotPassword(String  email);
 void setNetworkDelegate(NetworkDelegate networkDelegate);
 LiveData<List<Medication>> getAllMedications();
 LiveData<List<Medication>> getActiveMedications(long time);
 LiveData<List<Medication>> getInactiveMedications(long time);
 LiveData<Medication> getShowMedication(int medID);
 void updateMedication(Medication medicationModel);
 void takeMedication (String pillStock,int Id);
 void rescheduleMedication (Alarm alarm, int Id);


    void getAllMedication(LocalSource localSource);
 void insertMedication(Medication medicationModel);
 void deleteMedication(Medication medicationModel);
 void userExistence(String receiverEmail);
 void addHealthTaker(Request request, String healthTakerEmail);
}
