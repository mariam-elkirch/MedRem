package com.example.medred.addmedication.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.medred.R;
import com.example.medred.factory.AddMedicationActivity;


public class AddMedicationFinal extends Fragment {
    Spinner reasonSpinner;
    String itemReason;
    View view;
    AddMedicationActivity addMedicationActivity;
    CheckBox refillReminderCheckBox;
    EditText pillStockET, pillLeftEt;

    ConstraintLayout constraintFinal;
    String  pillStockItem , pillLeftItem;
    Button doneBtn;
    TimePicker refillTimePicker;
    int hour , minute;

    TextView refillReminderTV , dateRefill;
    DatePickerDialog picker;
    int selectedHour, selectedMinute;
    String format;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add_medication_final, container, false);

        // set all ids for the components
        pillLeftEt=view.findViewById(R.id.pillLeftET);
        pillStockET=view.findViewById(R.id.pillStockET);
        refillReminderCheckBox=view.findViewById(R.id.refillReminderCheckBox);
        constraintFinal=view.findViewById(R.id.constraintFinal);
        doneBtn=view.findViewById(R.id.doneBtn);
        refillReminderTV=view.findViewById(R.id.refillReminderTV);
        dateRefill=view.findViewById(R.id.dateRefill);

        refillReminderTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        selectedHour= hour;
                        selectedMinute=minute;
                        if (selectedHour == 0) {
                            selectedHour += 12;
                            format = "AM";
                        } else if (selectedHour == 12) {
                            format = "PM";
                        } else if (hour > 12) {
                            selectedHour -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }
                        //holder.alarmSetTV.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
                        dateRefill.setText(selectedHour+" : "+selectedMinute+" "+format);
                        dateRefill.setVisibility(View.VISIBLE);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), TimePickerDialog.THEME_HOLO_LIGHT, onTimeSetListener,selectedHour,selectedMinute,false);
                timePickerDialog.setTitle("Set Alarm");
                timePickerDialog.show();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleFinalFragment();
            }
        });

        refillReminderCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(constraintFinal.getVisibility() == View.VISIBLE){
                    constraintFinal.setVisibility(View.INVISIBLE);
                }
                else{
                    constraintFinal.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    public void handleFinalFragment(){

        pillStockItem=pillStockET.getText().toString();
        pillLeftItem=pillLeftEt.getText().toString();
        String refillStr = dateRefill.getText().toString();


        if(constraintFinal.getVisibility() == View.VISIBLE&&!pillStockItem.trim().isEmpty()&&!pillLeftItem.trim().isEmpty()&&!refillStr.trim().isEmpty()){
                Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
        }
        else if (constraintFinal.getVisibility() == View.INVISIBLE&&!pillStockItem.trim().isEmpty()){
                Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getContext(), "please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }




}