package com.example.medred.addmedication.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.medred.R;
import com.example.medred.model.Alarm;
import com.example.medred.model.Medication;

import java.util.ArrayList;


public class EditMedication extends Fragment {


    View view;
    EditText nameEditED;
    TextView alarmOneEdit,alarmTwoEdit,alarmThreeEdit,alarmFourEdit,alarmFiveEdit,alarmSixEdit;
    TextView startDateEdit,endDateEdit,showStartDateEdit,showEndDataEdit,showFrequency,showUnit;
    Spinner frequencySpinnerEdit,unitSpinnerEdit;
    Button  doneBtnEdit,cancelBtnEdit;
    Medication editMedication = new Medication();
    int editHour,editMinute;
    String format;
    Alarm alarmOne, alarmTwo,alarmThree,alarmFour,alarmFive,alarmSix;
    String alarmOneStr, alarmTwoStr,alarmThreeStr,alarmFourStr,alarmFiveStr,alarmSixStr;
    Dialog editDialog;
    Button deleteBtn , cancelBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_edit_medication, container, false);
        //set all ids
        nameEditED=view.findViewById(R.id.nameEditED);
        alarmOneEdit=view.findViewById(R.id.alarmOneEdit);
        alarmTwoEdit=view.findViewById(R.id.alarmTwoEdit);
        alarmThreeEdit=view.findViewById(R.id.alarmThreeEdit);
        alarmFourEdit=view.findViewById(R.id.alarmFourEdit);
        alarmFiveEdit=view.findViewById(R.id.alarmFiveEdit);
        alarmSixEdit=view.findViewById(R.id.alarmSixEdit);
        startDateEdit=view.findViewById(R.id.startDateEdit);
        endDateEdit=view.findViewById(R.id.endDateEdit);
        showStartDateEdit=view.findViewById(R.id.showStartDateEdit);
        showEndDataEdit=view.findViewById(R.id.showEndDataEdit);
        frequencySpinnerEdit=view.findViewById(R.id.frequencySpinnerEdit);
        unitSpinnerEdit=view.findViewById(R.id.unitSpinnerEdit);
        doneBtnEdit =view.findViewById(R.id.doneBtnEdit);
        cancelBtnEdit=view.findViewById(R.id.cancelBtnEdit);
        showFrequency=view.findViewById(R.id.showFrequency);
        showUnit=view.findViewById(R.id.showUnit);

        //get bundle
        Bundle bundle = getArguments();
        Medication eMedication= (Medication) bundle.getSerializable("edit");
        editMedication=eMedication;


        showMedication();
        doneBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpdate();
            }
        });



        return view;
    }

    void showMedication(){
        nameEditED.setText(editMedication.getName());
        //alarms
        if(0 >= editMedication.getSetAlarm().size()){
            alarmOneEdit.setText("Set Alarm");
        }else{
            alarmOneEdit.setText(editMedication.getSetAlarm().get(0).getHour()+":"+editMedication.getSetAlarm().get(0).getMinute()+":"+editMedication.getSetAlarm().get(0).getFormat());
        }

        if(1>=editMedication.getSetAlarm().size()){
            alarmTwoEdit.setText("Set Alarm");
        }else{
            alarmTwoEdit.setText(editMedication.getSetAlarm().get(1).getHour()+":"+editMedication.getSetAlarm().get(1).getMinute()+":"+editMedication.getSetAlarm().get(1).getFormat());
        }

        if(2>=editMedication.getSetAlarm().size()){
            alarmThreeEdit.setText("Set Alarm");
        }else{
            alarmThreeEdit.setText(editMedication.getSetAlarm().get(2).getHour()+":"+editMedication.getSetAlarm().get(2).getMinute()+":"+editMedication.getSetAlarm().get(2).getFormat());
        }

        if(3>=editMedication.getSetAlarm().size()){
            alarmFourEdit.setText("Set Alarm");
        }else{
            alarmFourEdit.setText(editMedication.getSetAlarm().get(3).getHour()+":"+editMedication.getSetAlarm().get(3).getMinute()+":"+editMedication.getSetAlarm().get(3).getFormat());
        }

        if(4>=editMedication.getSetAlarm().size()){
            alarmFiveEdit.setText("Set Alarm");
        }else{
            alarmFiveEdit.setText(editMedication.getSetAlarm().get(4).getHour()+":"+editMedication.getSetAlarm().get(4).getMinute()+":"+editMedication.getSetAlarm().get(4).getFormat());
        }

        if(5>=editMedication.getSetAlarm().size()){
            alarmSixEdit.setText("Set Alarm");
        }else{
            alarmSixEdit.setText(editMedication.getSetAlarm().get(5).getHour()+":"+editMedication.getSetAlarm().get(5).getMinute()+":"+editMedication.getSetAlarm().get(5).getFormat());
        }

        showStartDateEdit.setText(editMedication.getStartDate());
        showEndDataEdit.setText(editMedication.getEndDate());
        showUnit.setText(editMedication.getUnit());
        //show frequency
        switch(editMedication.getFrequency()){
            case 1:
                showFrequency.setText("As Needed");
                break;
            case 2:
                showFrequency.setText("EveryDay");
                break;
            case 3:
                showFrequency.setText("Specific Days");
                break;
            default:
                showFrequency.setText("Set Frequency");
        }


            alarmOneEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(alarmOneEdit.equals("Set Alarm")){
                    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            editHour = hour;
                            editMinute = minute;
                            if (editHour == 0) {
                                editHour += 12;
                                format = "AM";
                            } else if (editHour == 12) {
                                format = "PM";
                            } else if (hour > 12) {
                                editHour -= 12;
                                format = "PM";
                            } else {
                                format = "AM";
                            }
                            alarmOneEdit.setText(editHour + ":" + editMinute + ":" + format);
                            alarmOneStr = alarmOneEdit.getText().toString();
                            alarmOne = new Alarm(editHour, editMinute, format);
                        }
                    };
                }else{
                        editDialog =new Dialog(getContext());
                        editDialog.setContentView(R.layout.edit_dialog);
                        editDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        editDialog.setCancelable(false);
                        deleteBtn=editDialog.findViewById(R.id.deleteBtn);
                        cancelBtn=editDialog.findViewById(R.id.cancelBtn);
                        editDialog.show();
                        deleteBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Alarm al = editMedication.getSetAlarm().get(0);
                                al.setFormat(null);
                            }
                        });

                    }
                }
            });












    }


    void setUpdate(){
      String nameUpdated=  nameEditED.getText().toString();
      editMedication.setName(nameUpdated);





    }


}