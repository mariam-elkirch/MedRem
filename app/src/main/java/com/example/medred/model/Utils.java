package com.example.medred.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static String SHARED_PREF = "SHARED_PREFS";
    public static String UID_KEY = "UID";
    public static String IS_DEPENDANT_KEY = "IS_DEPENDANT";

    public static long convertDateToMillis(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        Calendar calendar = Calendar.getInstance();
        try{
            Date date = sdf.parse(dateStr);
            calendar.setTime(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }
    public  static long convertDateAndTimeToFinalTimeInMills(String timeAndDate){
       // String timeAndDate = day +"-" + month+"-" + year+" " + hour +":" + minutes ; ;// /-03-2022 12:34";//
        long currentTime = Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat sdf =  new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        long timeInMilliseconds;
        Date mDate = null;
        try {
            mDate = sdf.parse(timeAndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("TAG", "Date m datee: " + mDate);

        timeInMilliseconds = mDate.getTime();
        System.out.println("Date in milli :: " + timeInMilliseconds);
        long finalTime = timeInMilliseconds - currentTime;
        Log.i("TAG", "final time " + finalTime);
        return  finalTime;
    }
    public  static long convertDateAndTimeToTimeInMills(String timeAndDate){
        // String timeAndDate = day +"-" + month+"-" + year+" " + hour +":" + minutes ; ;// /-03-2022 12:34";//
        long currentTime = Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat sdf =  new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        long timeInMilliseconds;
        Date mDate = null;
        try {
            mDate = sdf.parse(timeAndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("TAG", "Date m datee: " + mDate);

        timeInMilliseconds = mDate.getTime();

        return  timeInMilliseconds;
    }
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
