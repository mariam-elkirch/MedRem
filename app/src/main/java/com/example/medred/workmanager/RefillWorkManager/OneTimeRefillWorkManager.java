package com.example.medred.workmanager.RefillWorkManager;

import static android.content.Context.NOTIFICATION_SERVICE;
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
import androidx.work.Data;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.medred.Home.view.HomeActivity;
import com.example.medred.R;
import com.example.medred.model.Medication;
import com.example.medred.model.Utils;
import com.example.medred.workmanager.AlarmWorkManager.ManageWorkManager;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class OneTimeRefillWorkManager extends Worker {
    private static Context context;
    private static List<Medication> refillMedications = null;


    public OneTimeRefillWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {

        Data inputData = getInputData();
        String medicationName = inputData.getString("medicationName");

        displayNotification(medicationName);
        getNextRefillReminder();
        return Result.success();
    }

    public static void setRefillMedications(List<Medication> medications) {
        Log.i("Refill", medications.size() + "");
        refillMedications = medications;
        Log.i("Refill", refillMedications.size() + "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNextRefillReminder();
        }
    }

    private void displayNotification(String keyword) {
        // TODO:(change HomeActivity to display medication activity)
        Intent notificationIntent = new Intent(getApplicationContext(), HomeActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 200,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("RefillReminders",
                    "RefillReminders", NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "RefillReminders")
                .setContentTitle(keyword + " Refill Alert")
                .setContentText("It's time to refill your medication stock")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_baseline_medical_services_24);


        assert notificationManager != null;
        notificationManager.notify(1, builder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getNextRefillReminder() {



        Log.i("Refill", "outside if" );
        if(refillMedications != null && refillMedications.size() > 0){
            WorkManager.getInstance().cancelAllWorkByTag("oneTimeRefillReminders");
            Log.i("Refill", "inside if");
            long currentTime = Calendar.getInstance().getTimeInMillis();
            long smallest = currentTime;
            String medicationName = null;
            String reminderAlarm = null;
            LocalDate localDate=LocalDate.now();

            for (int i = 0; i < refillMedications.size(); i++) {
                Log.i("Refill", "inside for loop");
                if(refillMedications.get(i).isRefillReminder()){
                    Log.i("Refill", "inside second if");
                    String alarmTime = localDate.getDayOfMonth()+ "-" + localDate.getMonthValue() + "-" + localDate.getYear() + " " +
                            refillMedications.get(i).getAlarmRefillTime().getHour() + ":" +
                            refillMedications.get(i).getAlarmRefillTime().getMinute()
                            + " " + refillMedications.get(i).getAlarmRefillTime().getFormat();

                    long timeInMills = Utils.convertDateAndTimeToTimeInMills(alarmTime);

                    int stock = Integer.parseInt(refillMedications.get(i).getPillStock());
                    int reminder = Integer.parseInt(refillMedications.get(i).getLeftPillReminder());
                    if (reminder >= stock && timeInMills - currentTime > 0 && timeInMills - currentTime < smallest) {
                        smallest = timeInMills - currentTime;
                        reminderAlarm = alarmTime;
                        Log.i("Refill", "Refill: FinfResut If "+reminderAlarm);
                        medicationName = refillMedications.get(i).getName();
                    }

                }
            }

            if (reminderAlarm != null) {
                Log.i("Refill", "Refill: In side smallest reminder method");
                ManageWorkManager.setOneTimeRefillRequest(context, reminderAlarm, medicationName);

            }
        }
    }
}