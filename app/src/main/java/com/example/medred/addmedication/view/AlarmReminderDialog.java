package com.example.medred.addmedication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.medred.R;
import com.example.medred.addmedication.presenter.AddMedicationPresenterInterface;
import com.example.medred.addmedication.presenter.MedicationPresenter;
import com.example.medred.db.ConcreteLocalSource;
import com.example.medred.model.Alarm;
import com.example.medred.model.Medication;
import com.example.medred.model.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AlarmReminderDialog extends AppCompatActivity implements AddMedicationViewInterface{
    String nameOfMedication;
    Dialog alarmDialog;
    ImageView imageDialog;
    TextView nameDialog;
    Button takeBtn,resecheduleBtn,skipBtn;
    Alarm alarm;
    int pHour,pMinute;
    String format;
    //String name;
    int pills, url;
    Medication medicationA;
    //saving
    AddMedicationPresenterInterface addMedicationPresenterInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_reminder_dialog);

        //trial
        nameOfMedication = getIntent().getExtras().getString("dialog");
        Log.d("TAG", "onCreate: "+nameOfMedication);

        //alarm reschedule dialog
        alarmDialog =new Dialog(this);
        alarmDialog.setContentView(R.layout.notification_dialog);
        alarmDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        alarmDialog.setCancelable(false);
        imageDialog=alarmDialog.findViewById(R.id.imageDialog);
        nameDialog=alarmDialog.findViewById(R.id.nameDialog);
        takeBtn=alarmDialog.findViewById(R.id.takeBtn);
        resecheduleBtn=alarmDialog.findViewById(R.id.resecheduleBtn);
        skipBtn=alarmDialog.findViewById(R.id.skipBtn);
        nameDialog.setText(nameOfMedication);
        imageDialog.setImageResource(url);

        alarmDialog.show();
        //room
        addMedicationPresenterInterface = new MedicationPresenter(this, Repository.getInstance(AlarmReminderDialog.this,null, ConcreteLocalSource.getInstance(AlarmReminderDialog.this)));

        //get object

        getMedicinePill(nameOfMedication);


        resecheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        pHour= hour;
                        pMinute=minute;
                        if (pHour == 0) {
                            pHour += 12;
                            format = "AM";
                        } else if (pHour == 12) {
                            format = "PM";
                        } else if (hour > 12) {
                            pHour -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }
                        alarm = new Alarm(pHour,pMinute,format);

                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), TimePickerDialog.THEME_HOLO_LIGHT, onTimeSetListener,pHour,pMinute,false);
                timePickerDialog.setTitle("Set Alarm");
                timePickerDialog.show();
                rescheduleMedication(alarm,nameOfMedication);



                alarmDialog.dismiss();
                finish();
            }
        });

        //take btn handler
        takeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pills = pills - 1;
                String p = String.valueOf(pills);
                takeMedication(p,nameOfMedication);
                Log.d("TAG", "onClick: pills numbers "+pills);
                Log.d("TAG", "onClick: name of medication  "+nameOfMedication);
                alarmDialog.dismiss();
                finish();
            }
        });
        //skip btn
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmDialog.dismiss();
                finish();
            }
        });







    }

    //get pill
    public void getMedicinePill(String name){
        LiveData<Medication> medicine = getPill(name);
        medicine.observe(this, new Observer<Medication>() {
            @Override
            public void onChanged(Medication medication) {
                showMedication(medication);
            }
        });
    }

    private void showMedication(Medication medication) {
        //pills =  Integer.getInteger(medication.getPillStock());
        medicationA = new Medication();
        medicationA=medication;
        pills=Integer.parseInt(medicationA.getPillStock());
        url = medicationA.getImageID();

    }

    @Override
    public void setMedicationView(Medication medicationModel) {

    }

    @Override
    public LiveData<Medication> getShowMed(int idMed) {
        return null;
    }

    @Override
    public void updateMedication(Medication medicationModel) {

    }

    @Override
    public void takeMedication(String pillStock, String name) {

        addMedicationPresenterInterface.takeMedication(pillStock, name);
    }

    @Override
    public void rescheduleMedication(Alarm alarm, String name) {
        addMedicationPresenterInterface.rescheduleMedication(alarm,name);
    }

    @Override
    public LiveData<Medication> getPill(String name) {
        return addMedicationPresenterInterface.getPill(name);
    }
}