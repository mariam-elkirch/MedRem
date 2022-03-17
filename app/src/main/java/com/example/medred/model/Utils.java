package com.example.medred.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

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

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
