package com.example.medred.workmanager.AlarmWorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.medred.Home.view.HomeActivity;
import com.example.medred.R;
import com.example.medred.model.Reminders;
import com.example.medred.model.Utils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class OneTimeWorker extends Worker {
    private static Context context;
    private static List<Reminders> remindersList = null;


    public OneTimeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        Log.i("TAG", "Inside final  dowork notification");
        displayNotification("mriam");
        //get next , setnext
       findTheRest();

        return Result.success();
    }

    public static void setMedicationList(List<Reminders> reminders) {
        remindersList = reminders;
       // findTheRest();

    }

    private void displayNotification(String keyword) {
        Intent notificationIntent = new Intent(getApplicationContext(), HomeActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 200, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle("Reminder" + keyword)
                .setContentText("You have to take your medicine")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_baseline_medical_services_24);


        assert notificationManager != null;
        notificationManager.notify(1, builder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void findTheRest() {
        //refrence from repo fing medication
        WorkManager.getInstance().cancelAllWorkByTag("alarms");
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long smallest = currentTime;
        String medicineName = null;
        String scheduledAlarm = null;
        LocalDate localDate=LocalDate.now();
      //  Calendar mycalobj = Calendar.getInstance();
        for (int i = 0; i < remindersList.size(); i++) {
            for (int alarm = 0; alarm < remindersList.get(i).getSetAlarm().size(); alarm++) {

                String alarmTime = localDate.getDayOfMonth()+ "-" + localDate.getMonthValue() + "-" + localDate.getYear() + " " +
                        remindersList.get(i).getSetAlarm().get(alarm).getHour() + ":" +
                        remindersList.get(i).getSetAlarm().get(alarm).getMinute()
                        + " " + remindersList.get(i).getSetAlarm().get(alarm).getFormat();
                Log.i("TAG", "In side findRest "+alarmTime);
                long timeInMills = Utils.convertDateAndTimeToTimeInMills(alarmTime);
                Log.i("TAG", "In side findRestMills "+timeInMills+"  "+alarmTime+" "+(timeInMills-currentTime));
                if (timeInMills - currentTime >= 0 && timeInMills - currentTime < smallest) {
                    smallest = timeInMills - currentTime;
                    scheduledAlarm = alarmTime;
                    Log.i("TAG", "FinfResut If "+scheduledAlarm);
                }
                medicineName = remindersList.get(i).getName();
            }
        }

        if (scheduledAlarm != null) {
            Log.i("TAG", "In side smallest reminder method");
            ManageWorkManager.setOneTimeRequest(context, scheduledAlarm, medicineName);

        }

        //
    }
}

