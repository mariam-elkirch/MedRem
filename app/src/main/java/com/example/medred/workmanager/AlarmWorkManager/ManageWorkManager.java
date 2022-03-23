package com.example.medred.workmanager.AlarmWorkManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;


import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;

import com.example.medred.model.Medication;
import com.example.medred.model.Utils;
import com.example.medred.workmanager.RefillWorkManager.OneTimeRefillWorkManager;

import java.util.concurrent.TimeUnit;

public class ManageWorkManager {
    public static  void setNext(){}//finf interval make set one time request for irits workers
    //set next for classs one time
    //2 worker periodic one time
    public static  void setPeriodicRequest(Context context){
        PeriodicWorkRequest periodicRequest = new PeriodicWorkRequest.Builder(WorkManager.class,4, TimeUnit.MINUTES)
                .setInitialDelay(10, TimeUnit.SECONDS)//each 3 min
                .addTag("periodic")
                .build();
        androidx.work.WorkManager.getInstance(context).enqueueUniquePeriodicWork("Mariam" ,
                ExistingPeriodicWorkPolicy.REPLACE,
                periodicRequest);
        Log.i("TAG", "In side periodic request setter");
    }
    public  static  void setOneTimeRequest(Context context, String time, String medicineName,int medicneId){
        Log.i("TAG", "In side one time request "+medicineName);
        Data data = new Data.Builder()
                .putString("medicine",medicineName)
                .putInt("id",medicneId)
                .build();
        long initialTime= Utils.convertDateAndTimeToFinalTimeInMills(time);
        OneTimeWorkRequest reminderRequest = new OneTimeWorkRequest.Builder(OneTimeWorker.class)
                .setInitialDelay(initialTime, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .addTag("alarms")
                .build();
        androidx.work.WorkManager.getInstance(context).enqueue(reminderRequest);
        Log.i("TAG", "In side one time request setter");
    }

    public  static  void setOneTimeRefillRequest(Context context, String time, String medicationName){
        Log.i("TAG", "setOneTimeRefillRequest: "+ medicationName);
        Data data = new Data.Builder()
                .putString("medicationName", medicationName)
                .build();

        long initialTime= Utils.convertDateAndTimeToFinalTimeInMills(time);

        OneTimeWorkRequest reminderRequest = new OneTimeWorkRequest.Builder(OneTimeRefillWorkManager.class)
                .setInitialDelay(initialTime, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .addTag("oneTimeRefillReminders")
                .build();

        androidx.work.WorkManager.getInstance(context).enqueue(reminderRequest);
        Log.i("Refill", "Inside oneTimeRefillReminder setter");
    }
}
