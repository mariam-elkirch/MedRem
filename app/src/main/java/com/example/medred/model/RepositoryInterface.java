package com.example.medred.model;

import androidx.lifecycle.LiveData;

import com.example.medred.db.LocalSource;
import com.example.medred.network.FirebaseManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public interface RepositoryInterface {
void authunticateUser(User user, FirebaseManager.FireBaseCallBack fireBaseCallBack);
 void RegisterationGoogle(GoogleSignInAccount account, FirebaseManager.FireBaseCallBack fireBaseCallBack);
 void loginEmailPassword(String email,String  password,FirebaseManager.FireBaseCallBack fireBaseCallBack);
 void forgotPassword(String  email);


 LiveData<List<Medication>> getAllMedications();
 void getAllMedication(LocalSource localSource);
 void insertMedication(Medication medicationModel);
 void deleteMedication(Medication medicationModel);
}
