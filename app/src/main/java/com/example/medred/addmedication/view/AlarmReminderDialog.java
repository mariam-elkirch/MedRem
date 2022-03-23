package com.example.medred.addmedication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.medred.R;
import com.example.medred.model.Medication;

public class AlarmReminderDialog extends AppCompatActivity {
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_reminder_dialog);

        //trial
        id = getIntent().getExtras().getInt("id");
//        Bundle bundle = new Bundle();//create bundle instance
//        bundle.putInt("ID",idOfMedication);
//        ShowMedication showMedication = new ShowMedication();
//        showMedication.setArguments(bundle);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2,showMedication).commit();

        Log.d("TAG", "onCreate: "+id);
    }
}