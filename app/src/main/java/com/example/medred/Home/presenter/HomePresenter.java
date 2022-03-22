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
import com.example.medred.model.Medication;
import com.example.medred.model.Reminders;
import com.example.medred.model.RepositoryInterface;
import com.example.medred.model.Utils;
import com.example.medred.workmanager.AlarmWorkManager.OneTimeWorker;
import com.example.medred.workmanager.AlarmWorkManager.WorkManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.*;

public class HomePresenter implements HomePresenterInterface{
   HomeViewInterface view;
    RepositoryInterface repository;

    public HomePresenter(HomeViewInterface view, RepositoryInterface repository) {
      this.view=view;
        this.repository=repository;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getCalenderMedications(Long time,LifecycleOwner owner) {
       // repository.getSpecificDayCalenderMedications(time,"Friday");
        LiveData<List<Medication>>med=repository.getSpecificDayCalenderMedications(time,"Friday");


       repository.getCalenderMedications(time).observe(owner, medications -> {
           view.getCalenderMeds(medications);
           if(medications!=null && medications.size()>0)
               OneTimeWorker.setMedicationList(getReminders(medications));

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
       for(int medication=0;medication<medications.size();medication++){
           for(int alarm=0;alarm<medications.get(medication).getSetAlarm().size();alarm++){
               //sort alarms make date fixed as i want time to be sorted
              // if(medications.get(medication).getFrequency()==3 ||medications.get(medication).getFrequency()==4){
                   alarmTime = localDate.getDayOfMonth() + "-" + localDate.getMonthValue() + "-" + localDate.getYear() + " " +
                           medications.get(medication).getSetAlarm().get(alarm).getHour() + ":" +
                           medications.get(medication).getSetAlarm().get(alarm).getMinute()
                           + " " + medications.get(medication).getSetAlarm().get(alarm).getFormat();

               Alarm alarm1=new Alarm( medications.get(medication).getSetAlarm().get(alarm).getHour()
               , medications.get(medication).getSetAlarm().get(alarm).getMinute(),
                       medications.get(medication).getSetAlarm().get(alarm).getFormat());

               ArrayList<Alarm>alarmList=new ArrayList<>();
               alarmList.add(alarm1);

               long timeInMills= Utils.convertDateAndTimeToTimeInMills(alarmTime);

                 reminder=new Reminders(medications.get(medication).getName()
                ,medications.get(medication).getNumberOfDoses(),
                       alarmList,medications.get(medication).getImageID(),
                        medications.get(medication).getStrength(),
                        medications.get(medication).getUnit(),medications.get(medication).getId(),
                        timeInMills);
               sortReminders.add(reminder);//before sort but if medication has many alarms put it many times
           }

       }

        //Log.i("TAG",sortReminders.get(0).getName());
        sort(sortReminders, Comparator.comparingLong(Reminders::getTimeImMills));
        //Log.i("TAG",sortReminders.get(0).getName());

       return sortReminders;
    }





}
