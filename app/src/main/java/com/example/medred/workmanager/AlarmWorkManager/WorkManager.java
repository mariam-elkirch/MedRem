package com.example.medred.workmanager.AlarmWorkManager;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.medred.workmanager.RefillWorkManager.OneTimeRefillWorkManager;

import static com.example.medred.workmanager.RefillWorkManager.OneTimeRefillWorkManager.setRefillMedications;

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
        Log.i("TAG", "Inside periodic doWork");
        OneTimeWorker.findTheRest();
        OneTimeRefillWorkManager.getNextRefillReminder();

        Log.i("TAG", "After one time request");
        return Result.success();

    }



}
