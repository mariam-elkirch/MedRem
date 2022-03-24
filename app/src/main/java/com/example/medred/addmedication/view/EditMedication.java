package com.example.medred.addmedication.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medred.Home.view.HomeActivity;
import com.example.medred.MainActivity;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class EditMedication extends Fragment  implements AddMedicationViewInterface {


    View view;
    EditText nameEditED;
    TextView alarmOneEdit,alarmTwoEdit,alarmThreeEdit,alarmFourEdit,alarmFiveEdit,alarmSixEdit;
    TextView startDateEdit,endDateEdit,showStartDateEdit,showEndDataEdit,showFrequency,showUnit;
    Spinner frequencySpinnerEdit,unitSpinnerEdit;
    Button  doneBtnEdit,cancelBtnEdit;
    Medication editMedication = new Medication();
    int editHour,editMinute;
    String format;
    Alarm alarmOne, alarmTwo,alarmThree,alarmFour,alarmFive,alarmSix , alarmZero;
    String alarmOneStr, alarmTwoStr,alarmThreeStr,alarmFourStr,alarmFiveStr,alarmSixStr;
    Dialog editDialog;
    Button deleteBtn , cancelBtn , doneEditBtn;
    TimePicker alarmPickerEdit;
    ArrayList<Alarm>alarmArrayListEdit =new ArrayList<Alarm>();
    DatePickerDialog editPicker;
    String startDateStr,endDateStr;
    String frequencyItem,unitMedItem;
    int fragmentChoose;
    boolean[] selectedDays;
    String[] strFirst ={"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
    ArrayList<Integer> arrayListDays = new ArrayList<>();
    String days;
    //save in  room and firebase
    AddMedicationPresenterInterface addMedicationPresenterInterface;
    private FirebaseUser user;
    private DatabaseReference reference , databaseReference;
    private String userId;
    int ID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_edit_medication, container, false);
        //firebase
        //try firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("user");
        userId=user.getUid();
        //room
        addMedicationPresenterInterface = new MedicationPresenter(this, Repository.getInstance(getContext(),null, ConcreteLocalSource.getInstance(getContext())));
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
       // cancelBtnEdit=view.findViewById(R.id.cancelBtnEdit);
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
//                Intent intent = new Intent(getContext(), HomeActivity.class);
//                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    void showMedication(){
        nameEditED.setText(editMedication.getName());
        //alarms
        //try
        alarmArrayListEdit=editMedication.getSetAlarm();
            if(0 >= editMedication.getSetAlarm().size()||editMedication.getSetAlarm().get(0)==null){
                alarmOneEdit.setText("Set Alarm");
            }else{
                alarmOneEdit.setText(editMedication.getSetAlarm().get(0).getHour()+":"+editMedication.getSetAlarm().get(0).getMinute()+":"+editMedication.getSetAlarm().get(0).getFormat());
            }

            if(1>=editMedication.getSetAlarm().size()||editMedication.getSetAlarm().get(1)==null){
                alarmTwoEdit.setText("Set Alarm");
            }else{
                alarmTwoEdit.setText(editMedication.getSetAlarm().get(1).getHour()+":"+editMedication.getSetAlarm().get(1).getMinute()+":"+editMedication.getSetAlarm().get(1).getFormat());
            }

            if(2>=editMedication.getSetAlarm().size()||editMedication.getSetAlarm().get(2)==null){
                alarmThreeEdit.setText("Set Alarm");
            }else{
                alarmThreeEdit.setText(editMedication.getSetAlarm().get(2).getHour()+":"+editMedication.getSetAlarm().get(2).getMinute()+":"+editMedication.getSetAlarm().get(2).getFormat());
            }

            if(3>=editMedication.getSetAlarm().size()||editMedication.getSetAlarm().get(3)==null){
                alarmFourEdit.setText("Set Alarm");
            }else{
                alarmFourEdit.setText(editMedication.getSetAlarm().get(3).getHour()+":"+editMedication.getSetAlarm().get(3).getMinute()+":"+editMedication.getSetAlarm().get(3).getFormat());
            }

            if(4>=editMedication.getSetAlarm().size()||editMedication.getSetAlarm().get(4)==null){
                alarmFiveEdit.setText("Set Alarm");
            }else{
                alarmFiveEdit.setText(editMedication.getSetAlarm().get(4).getHour()+":"+editMedication.getSetAlarm().get(4).getMinute()+":"+editMedication.getSetAlarm().get(4).getFormat());
            }

            if(5>=editMedication.getSetAlarm().size()||editMedication.getSetAlarm().get(5)==null){
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
        //set alarms
        editDialog =new Dialog(getContext());
        editDialog.setContentView(R.layout.edit_alarm_dialog);
        editDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        editDialog.setCancelable(false);
        deleteBtn=editDialog.findViewById(R.id.deleteBtn);
        cancelBtn=editDialog.findViewById(R.id.cancelBtn);
        doneEditBtn=editDialog.findViewById(R.id.doneEditBtn);
        alarmPickerEdit=editDialog.findViewById(R.id.alarmPickerEdit);
            alarmOneEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editDialog.show();
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editDialog.dismiss();
                        }
                    });
                    deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            alarmOneEdit.setText("Set Alarm");

                            editDialog.dismiss();
                        }
                    });
                    doneEditBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (Build.VERSION.SDK_INT >= 23 ){
                                editHour = alarmPickerEdit.getHour();
                                editMinute = alarmPickerEdit.getMinute();
                            }
                            else{
                                editHour = alarmPickerEdit.getCurrentHour();
                                editMinute = alarmPickerEdit.getCurrentMinute();
                            }
                            if (editHour == 0) {
                                editHour += 12;
                                format = "AM";
                            } else if (editHour == 12) {
                                format = "PM";
                            } else if (editHour > 12) {
                                editHour -= 12;
                                format = "PM";
                            } else {
                                format = "AM";
                            }
                            alarmOneEdit.setText(editHour+":"+editMinute+":"+format);
                              alarmOne=new Alarm(editHour,editMinute,format);

                            editDialog.dismiss();

                            editDialog.dismiss();

                        }
                    });
                }
            });

            alarmTwoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog.show();
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editDialog.dismiss();
                    }
                });
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alarmTwoEdit.setText("Set Alarm");

                        editDialog.dismiss();
                    }
                });
                doneEditBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Build.VERSION.SDK_INT >= 23 ){
                            editHour = alarmPickerEdit.getHour();
                            editMinute = alarmPickerEdit.getMinute();
                        }
                        else{
                            editHour = alarmPickerEdit.getCurrentHour();
                            editMinute = alarmPickerEdit.getCurrentMinute();
                        }
                        if (editHour == 0) {
                            editHour += 12;
                            format = "AM";
                        } else if (editHour == 12) {
                            format = "PM";
                        } else if (editHour > 12) {
                            editHour -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }
                        alarmTwoEdit.setText(editHour+":"+editMinute+":"+format);
                          alarmTwo=new Alarm(editHour,editMinute,format);

                        editDialog.dismiss();

                        editDialog.dismiss();

                    }
                });
            }
        });

            alarmThreeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog.show();
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editDialog.dismiss();
                    }
                });
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alarmThreeEdit.setText("Set Alarm");

                        editDialog.dismiss();
                    }
                });
                doneEditBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Build.VERSION.SDK_INT >= 23 ){
                            editHour = alarmPickerEdit.getHour();
                            editMinute = alarmPickerEdit.getMinute();
                        }
                        else{
                            editHour = alarmPickerEdit.getCurrentHour();
                            editMinute = alarmPickerEdit.getCurrentMinute();
                        }
                        if (editHour == 0) {
                            editHour += 12;
                            format = "AM";
                        } else if (editHour == 12) {
                            format = "PM";
                        } else if (editHour > 12) {
                            editHour -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }
                        alarmThreeEdit.setText(editHour+":"+editMinute+":"+format);
                          alarmThree=new Alarm(editHour,editMinute,format);

                        editDialog.dismiss();

                        editDialog.dismiss();

                    }
                });
            }
        });

            alarmFourEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog.show();
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editDialog.dismiss();
                    }
                });
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alarmFourEdit.setText("Set Alarm");

                        editDialog.dismiss();
                    }
                });
                doneEditBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Build.VERSION.SDK_INT >= 23 ){
                            editHour = alarmPickerEdit.getHour();
                            editMinute = alarmPickerEdit.getMinute();
                        }
                        else{
                            editHour = alarmPickerEdit.getCurrentHour();
                            editMinute = alarmPickerEdit.getCurrentMinute();
                        }
                        if (editHour == 0) {
                            editHour += 12;
                            format = "AM";
                        } else if (editHour == 12) {
                            format = "PM";
                        } else if (editHour > 12) {
                            editHour -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }
                        alarmFourEdit.setText(editHour+":"+editMinute+":"+format);
                          alarmFour=new Alarm(editHour,editMinute,format);

                        editDialog.dismiss();

                        editDialog.dismiss();

                    }
                });
            }
        });

            alarmFiveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog.show();
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editDialog.dismiss();
                    }
                });
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alarmFiveEdit.setText("Set Alarm");

                        editDialog.dismiss();
                    }
                });
                doneEditBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Build.VERSION.SDK_INT >= 23 ){
                            editHour = alarmPickerEdit.getHour();
                            editMinute = alarmPickerEdit.getMinute();
                        }
                        else{
                            editHour = alarmPickerEdit.getCurrentHour();
                            editMinute = alarmPickerEdit.getCurrentMinute();
                        }
                        if (editHour == 0) {
                            editHour += 12;
                            format = "AM";
                        } else if (editHour == 12) {
                            format = "PM";
                        } else if (editHour > 12) {
                            editHour -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }
                        alarmFiveEdit.setText(editHour+":"+editMinute+":"+format);
                          alarmFive=new Alarm(editHour,editMinute,format);

                        editDialog.dismiss();

                        editDialog.dismiss();

                    }
                });
            }
        });

            alarmSixEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog.show();
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editDialog.dismiss();
                    }
                });
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alarmSixEdit.setText("Set Alarm");

                        editDialog.dismiss();
                    }
                });
                doneEditBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Build.VERSION.SDK_INT >= 23 ){
                            editHour = alarmPickerEdit.getHour();
                            editMinute = alarmPickerEdit.getMinute();
                        }
                        else{
                            editHour = alarmPickerEdit.getCurrentHour();
                            editMinute = alarmPickerEdit.getCurrentMinute();
                        }
                        if (editHour == 0) {
                            editHour += 12;
                            format = "AM";
                        } else if (editHour == 12) {
                            format = "PM";
                        } else if (editHour > 12) {
                            editHour -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }
                        alarmSixEdit.setText(editHour+":"+editMinute+":"+format);
                          alarmSix=new Alarm(editHour,editMinute,format);

                        editDialog.dismiss();

                        editDialog.dismiss();

                    }
                });
            }
        });


            startDateEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    editPicker = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    startDateStr = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                    showStartDateEdit.setText(startDateStr);
                                }
                            }, year, month, day);
                    editPicker.show();
                }
            });

            endDateEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    editPicker = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    endDateStr = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                    showEndDataEdit.setText(endDateStr);
                                }
                            }, year, month, day);
                    editPicker.show();
                }
            });

        frequencySpinnerEdit=view.findViewById(R.id.frequencySpinnerEdit);
        ArrayAdapter<CharSequence> arrayAdapterFreq = ArrayAdapter.createFromResource(getContext(),R.array.frequencyItemSpinner,android.R.layout.simple_spinner_item);
        arrayAdapterFreq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinnerEdit.setAdapter(arrayAdapterFreq);
        frequencySpinnerEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                frequencyItem=adapterView.getItemAtPosition(i).toString();
                switch (frequencyItem){

                    case "As Needed":
                        fragmentChoose=1;
                        days=("null");
                        break;
                    case "EveryDay":
                        fragmentChoose=2;
                        days="EveryDay";
                        break;
                    case"Days Interval":
                        fragmentChoose=3;
                        break;
                    case"Specific Day":
                        fragmentChoose=3;
                        break;
                    default:
                        //Toast.makeText(getContext(), "no fragment selected" + frequencyItem, Toast.LENGTH_SHORT).show();
                }
                showFrequency.setText(frequencyItem);
                //dialg date
                if(fragmentChoose==3){
                    selectedDays = new boolean[strFirst.length];
                    selectedDays = new boolean[strFirst.length];
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Select Day");
                    builder.setCancelable(false);

                    builder.setMultiChoiceItems(strFirst, selectedDays, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            if (b) {
                                arrayListDays.add(i);
                                Collections.sort(arrayListDays);
                            } else {
                                arrayListDays.remove(Integer.valueOf(i));
                            }
                        }
                    });

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int j = 0; j < arrayListDays.size(); j++) {
                                stringBuilder.append(strFirst[arrayListDays.get(j)]);
                                if (j != arrayListDays.size() - 1) {
                                    stringBuilder.append(", ");
                                }
                            }

                            days=stringBuilder.toString();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (int j = 0; j < selectedDays.length; j++) {
                                selectedDays[j] = false;
                                arrayListDays.clear();
                                days=("");
                            }
                        }
                    });
                    builder.show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        unitSpinnerEdit=view.findViewById(R.id.unitSpinnerEdit);
        ArrayAdapter<CharSequence> arrayAdapterUnit = ArrayAdapter.createFromResource(getContext(),R.array.unitItemSpinner,android.R.layout.simple_spinner_item);
        arrayAdapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinnerEdit.setAdapter(arrayAdapterUnit);
        unitSpinnerEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unitMedItem=adapterView.getItemAtPosition(i).toString();
                switch (unitMedItem){

                    case "g":
                        Log.d("TAG", "onItemSelected: "+unitMedItem);
                        break;
                    case "mg":
                        Log.d("TAG","onItemSelected: "+unitMedItem);
                        break;
                    case"mcg":
                        Log.d("TAG", "onItemSelected: "+unitMedItem);
                        break;
                    case"ml":
                        Log.d("TAG", "onItemSelected: "+unitMedItem);
                        break;
                    default:
                        Toast.makeText(getContext(), "no fragment selected" + unitMedItem, Toast.LENGTH_SHORT).show();
                }
                showUnit.setText(unitMedItem);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }


    void setUpdate(){
      String nameUpdated=  nameEditED.getText().toString();
      //alarm updates
      alarmOneStr=alarmOneEdit.getText().toString();
      alarmTwoStr=alarmTwoEdit.getText().toString();
      alarmThreeStr=alarmThreeEdit.getText().toString();
      alarmFourStr=alarmFourEdit.getText().toString();
      alarmFiveStr=alarmFiveEdit.getText().toString();
      alarmSixStr=alarmSixEdit.getText().toString();
        alarmArrayListEdit.clear();

      if(!alarmOneStr.equals("Set Alarm")){
          if(alarmOne!=null){
              alarmArrayListEdit.add(alarmOne);
          }else{
              String[] alOne = alarmOneStr.split(":");
              alarmOne=new Alarm(Integer.parseInt(alOne[0]),Integer.parseInt(alOne[1]),alOne[2]);
              alarmArrayListEdit.add(alarmOne);
          }
      }
        if(!alarmTwoStr.equals("Set Alarm")){
            if(alarmTwo!=null){
                alarmArrayListEdit.add(alarmTwo);
            }else{
                String[] alTwo = alarmTwoStr.split(":");
                alarmTwo=new Alarm(Integer.parseInt(alTwo[0]),Integer.parseInt(alTwo[1]),alTwo[2]);
                alarmArrayListEdit.add(alarmTwo);
            }
        }
        if(!alarmThreeStr.equals("Set Alarm")){
            if(alarmThree!=null){
                alarmArrayListEdit.add(alarmThree);
            }else{
                String[] alThree = alarmThreeStr.split(":");
                alarmThree=new Alarm(Integer.parseInt(alThree[0]),Integer.parseInt(alThree[1]),alThree[2]);
                alarmArrayListEdit.add(alarmThree);
            }
        }
        if(!alarmFourStr.equals("Set Alarm")){
            if(alarmFour!=null){
                alarmArrayListEdit.add(alarmFour);
            }else{
                String[] alFour = alarmFourStr.split(":");
                alarmFour=new Alarm(Integer.parseInt(alFour[0]),Integer.parseInt(alFour[1]),alFour[2]);
                alarmArrayListEdit.add(alarmFour);
            }
        }
        if(!alarmFiveStr.equals("Set Alarm")){
            if(alarmFive!=null){
                alarmArrayListEdit.add(alarmFive);
            }else{
                String[] alFive = alarmFiveStr.split(":");
                alarmFive=new Alarm(Integer.parseInt(alFive[0]),Integer.parseInt(alFive[1]),alFive[2]);
                alarmArrayListEdit.add(alarmFive);
            }
        }
        if(!alarmSixStr.equals("Set Alarm")){
            if(alarmSix!=null){
                alarmArrayListEdit.add(alarmSix);
            }else{
                String[] alSix = alarmSixStr.split(":");
                alarmSix=new Alarm(Integer.parseInt(alSix[0]),Integer.parseInt(alSix[1]),alSix[2]);
                alarmArrayListEdit.add(alarmSix);
            }
        }
        for(int i =0 ; i < alarmArrayListEdit.size();i++){
            Log.d("TAG", "setUpdate: "+alarmArrayListEdit.get(i).getHour());
            Log.d("TAG", "setUpdate: "+alarmArrayListEdit.get(i).getMinute());
            Log.d("TAG", "setUpdate: "+alarmArrayListEdit.get(i).getFormat());
        }

        if(startDateStr==null){
            startDateStr=showStartDateEdit.getText().toString();
            Log.d("TAG", "setUpdate: "+startDateStr);

        }
        else{
            Log.d("TAG", "setUpdate: "+startDateStr);
        }
        if(endDateStr==null){
            endDateStr=showEndDataEdit.getText().toString();
            Log.d("TAG", "setUpdate: "+endDateStr);
        }
        else{
            Log.d("TAG", "setUpdate: "+endDateStr);
        }

        Log.d("TAG", "setUpdate: "+days);




            editMedication.setDays(days);

        editMedication.setName(nameUpdated);
        editMedication.setSetAlarm(alarmArrayListEdit);
        editMedication.setStartDate(startDateStr);
        editMedication.setEndDate(endDateStr);
        editMedication.setUnit(unitMedItem);
        editMedication.setFrequency(fragmentChoose);

        //save in room and database
        updateMedication(editMedication);
        reference.child(userId).child("medication").child(editMedication.getName()).setValue(editMedication);
        Toast.makeText(getContext(), "Medication is edited successfully", Toast.LENGTH_SHORT).show();

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
        addMedicationPresenterInterface.updateMedication(medicationModel);
    }

    @Override
    public void takeMedication(String pillStock, String name) {

    }

    @Override
    public void rescheduleMedication(ArrayList<Alarm> alarm, String name) {

    }

    @Override
    public LiveData<Medication> getPill(String name) {
return null;
    }


}