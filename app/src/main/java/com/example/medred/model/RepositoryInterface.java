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
 void getAllMedication(LocalSource localSource);
 void insertMedication(Medication medicationModel);
 void deleteMedication(Medication medicationModel);
 void userExistence(String receiverEmail);
 void addHealthTaker(String healthTakerEmail);
 void getRequests();
 void acceptRequest(Request request);
 void rejectRequest(Request request);
 void getHealthTakers();
 void getDependants();
 void deleteHealthTaker(HealthTaker healthTaker);
 void deleteDependant(Dependant dependant);
 void addHealthTaker(Request request, String healthTakerEmail);
 LiveData<List<Medication>> getCalenderMedications(long time);
 void getFirebaseCalenderMedications(long time);
 LiveData<List<Medication>> getSpecificDayCalenderMedications(long time,String day);
}
