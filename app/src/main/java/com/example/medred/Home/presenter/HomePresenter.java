package com.example.medred.Home.presenter;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.example.medred.Home.view.HomeViewInterface;
import com.example.medred.model.Alarm;
import com.example.medred.model.Dependant;
import com.example.medred.model.HealthTaker;
import com.example.medred.model.Medication;
import com.example.medred.model.Reminders;
import com.example.medred.model.RepositoryInterface;
import com.example.medred.model.Request;
import com.example.medred.model.Utils;
import com.example.medred.network.NetworkDelegate;
import com.example.medred.workmanager.AlarmWorkManager.OneTimeWorker;
import com.example.medred.workmanager.AlarmWorkManager.WorkManager;
import com.example.medred.workmanager.RefillWorkManager.OneTimeRefillWorkManager;

import java.net.NetworkInterface;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.*;

public class HomePresenter implements HomePresenterInterface, NetworkDelegate {
   HomeViewInterface view;
    RepositoryInterface repository;
    LifecycleOwner myowner;
    long myTime;
    public HomePresenter(HomeViewInterface view, RepositoryInterface repository) {
      this.view=view;
        this.repository=repository;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getCalenderMedications(Long time,LifecycleOwner owner) {
       // repository.getSpecificDayCalenderMedications(time,"Friday");
     /*  repository.getSpecificDayCalenderMedications(time,"%Friday%").observe(owner, medications -> {
           view.getCalenderMeds(medications);
          if(medications != null && medications.size() > 0){
               OneTimeRefillWorkManager.setRefillMedications(medications);
               Log.i("Refill", "list Refill");
           }
       });
*/
       repository.getCalenderMedications(time).observe(owner, medications -> {

           view.getCalenderMeds(medications);
           if(medications!=null && medications.size()>0)
               OneTimeWorker.setMedicationList(getReminders(medications));

       });
       repository.getActiveMedications(time).observe(owner, medications -> {

            if(medications != null && medications.size() > 0){
                OneTimeRefillWorkManager.setRefillMedications(medications);
                Log.i("Refill", "list Refill");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Reminders> getReminders(List<Medication> medications) {
        //type of med , long time user insert from calender ang compare it with get days

            LocalDate localDate=LocalDate.now();

        List<Reminders>sortReminders=new ArrayList<Reminders>();
        String alarmTime;
        Reminders reminder=null;
       for(int medication=0;medication<medications.size();medication++) {
           if (medications.get(medication).getSetAlarm() != null) {
               for (int alarm = 0; alarm < medications.get(medication).getSetAlarm().size(); alarm++) {
                   //sort alarms make date fixed as i want time to be sorted
                   // if(medications.get(medication).getFrequency()==3 ||medications.get(medication).getFrequency()==4){
                   if (medications.get(medication).getSetAlarm().get(alarm) != null) {
                       alarmTime = localDate.getDayOfMonth() + "-" + localDate.getMonthValue() + "-" + localDate.getYear() + " " +
                               medications.get(medication).getSetAlarm().get(alarm).getHour() + ":" +
                               medications.get(medication).getSetAlarm().get(alarm).getMinute()
                               + " " + medications.get(medication).getSetAlarm().get(alarm).getFormat();

                       Alarm alarm1 = new Alarm(medications.get(medication).getSetAlarm().get(alarm).getHour()
                               , medications.get(medication).getSetAlarm().get(alarm).getMinute(),
                               medications.get(medication).getSetAlarm().get(alarm).getFormat());

                       ArrayList<Alarm> alarmList = new ArrayList<>();
                       alarmList.add(alarm1);

                       long timeInMills = Utils.convertDateAndTimeToTimeInMills(alarmTime);

                       reminder = new Reminders(medications.get(medication).getName()
                               , medications.get(medication).getNumberOfDoses(),
                               alarmList, medications.get(medication).getImageID(),
                               medications.get(medication).getStrength(),
                               medications.get(medication).getUnit(), medications.get(medication).getId(),
                               timeInMills);
                       sortReminders.add(reminder);//before sort but if medication has many alarms put it many times
                   }
               }
           }
       }
        //Log.i("TAG",sortReminders.get(0).getName());
        sort(sortReminders, Comparator.comparingLong(Reminders::getTimeImMills));
        //Log.i("TAG",sortReminders.get(0).getName());

       return sortReminders;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getFirebaseMedications(long time, LifecycleOwner owner) {
        Log.i("TAG", "getmedication Presenter");
        repository.setNetworkDelegate(this);
        repository.getFirebaseMedications();
        myowner=owner;
        myTime=time;
       /*repository.getFirebaseMedications((stored, medicationList) ->
    {
            view.getCalenderMeds(medicationList);
            if(medicationList!=null && medicationList.size()>0)
                OneTimeWorker.setMedicationList(getReminders(medicationList));

        });*/
       ///refill
          }


    @Override
    public void onSuccessResult(ArrayList<Medication> medicationModel) {

    }

    @Override
    public void onFailureResult(String errorMsg) {

    }

    @Override
    public void isUserExist(boolean userExistence, String receiverId) {

    }

    @Override
    public void onSuccessRequests(List<Request> requests) {

    }

    @Override
    public void onFailureRequests(String errorMsg) {

    }

    @Override
    public void onSuccessAcceptingRequest(Boolean isAccepted) {

    }

    @Override
    public void onSuccessRejectingRequest(Boolean isRejected) {

    }

    @Override
    public void onSuccessGettingHealthTakers(List<HealthTaker> healthTakers) {

    }

    @Override
    public void onSuccessGettingDependants(List<Dependant> dependants) {

    }

    @Override
    public void onDeletingHealthTaker(boolean isDeleted) {

    }

    @Override
    public void onDeletingDependant(boolean isDeleted) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSuccessMedicationList(List<Medication> medicationModel) {
        List<Medication>medicationList=new ArrayList<>();
        if(medicationModel !=null){
        for (int i=0;i<medicationModel.size();i++){
            if(myTime >= medicationModel.get(i).getStartDateInMillis() && myTime<=medicationModel.get(i).getEndDateInMillis()
            && medicationModel.get(i).isActive()){
                 medicationList.add(medicationModel.get(i));

            }}
        }

     view.getCalenderMeds(medicationList);
        if(  medicationList!=null &&   medicationList.size()>0)
            OneTimeWorker.setMedicationList(getReminders(  medicationList));
        Long time= Calendar.getInstance().getTimeInMillis();
        repository.getActiveMedications(time).observe(myowner, medications -> {

            if(medications != null && medications.size() > 0){
                OneTimeRefillWorkManager.setRefillMedications(medications);
                Log.i("Refill", "list Refill");
            }
        });
    }
}
