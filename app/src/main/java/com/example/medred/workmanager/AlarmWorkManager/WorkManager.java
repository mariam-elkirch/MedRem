package com.example.medred.workmanager.AlarmWorkManager;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkManager extends Worker {
    public static final String DATA = "repeat";
    public static final String INITIAL = "initial";
    private static final String CHANNEL_ID = "100";
    Context mcontext;

    public WorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mcontext = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        Log.i("TAG", "Inside periodic dowork");
        String timeAndDate = 21 +"-" + 3+"-" + 2022+" " + 8 +":" + 8+" PM" ;
        //getnext and set next in Myworkmanager
        OneTimeWorker.findTheRest();
       // ManageWorkManager.setOneTimeRequest(mcontext,timeAndDate,"panadol");
        Log.i("TAG", "after ont time reqest");
        //pushPeriodicNotification();
        return Result.success();

    }



}
