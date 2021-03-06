package com.example.medred.addmedication.view;

import static com.example.medred.addmedication.view.AddMedicationActivity.medicationMain;
import static com.example.medred.model.Utils.convertDateToMillis;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;


import com.example.medred.R;
import com.example.medred.model.Medication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class IntervalFragment extends Fragment {
    View view;


    Button nextBtnInterval;
    String itemDose;
    public static int numberOfDosesInt;
    TextView startDateInt , endDateInt , startInt , endInt;
    DatePickerDialog picker;
    Medication intervalMedication = new Medication();
    String[] strFirst ={"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
    TextView chooseDayInt , choosenInt;
    boolean[] selectedDays;
    ArrayList<Integer> arrayListDays = new ArrayList<>();


    Spinner doseSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_interval, container, false);

        Bundle bundle = getArguments();
        Medication receiveMedication= (Medication) bundle.getSerializable("interval");
        intervalMedication=receiveMedication;
        //handle the next button click
        nextBtnInterval = view.findViewById(R.id.nextBtnInterval);
        nextBtnInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setCheckedSwitch();
                checkInterval();
            }
        });
        //spinner
        doseSpinner= view.findViewById(R.id.doseSpinnerInt);
        ArrayAdapter<CharSequence> arrAdapterCycle= ArrayAdapter.createFromResource(getContext(),R.array.doseItemSpinner,android.R.layout.simple_spinner_item);
        arrAdapterCycle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doseSpinner.setAdapter(arrAdapterCycle);
        doseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemDose= adapterView.getItemAtPosition(i).toString();
                switch(itemDose){
                    case "Choose Dose":
                        Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                        break;
                    case "1":
                        numberOfDosesInt=1;
                        break;
                    case "2":
                        numberOfDosesInt=2;
                        break;
                    case "3":
                        numberOfDosesInt=3;
                        break;
                    case "4":
                        numberOfDosesInt=4;
                        break;
                    case "5":
                        numberOfDosesInt=5;
                        break;
                    case "6":
                        numberOfDosesInt=6;
                        break;
                    default:
                        numberOfDosesInt=0;
                        Toast.makeText(getContext(), "you must select a dose", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        startDateInt = view.findViewById(R.id.startClickInt);
        startInt= view.findViewById(R.id.startInt);
        startDateInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String str = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                startInt.setText(str);
                            }
                        }, year, month, day);
                picker.show();
                startInt.setVisibility(View.VISIBLE);
            }
        });


        endDateInt= view.findViewById(R.id.endClickInt);
        endInt=view.findViewById(R.id.endInt);
        endDateInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String str = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                endInt.setText(str);
                            }
                        }, year, month, day);
                picker.show();
                endInt.setVisibility(View.VISIBLE);
            }
        });

//check
        chooseDayInt=view.findViewById(R.id.chooseDayInt);
        choosenInt=view.findViewById(R.id.choosenInt);
        selectedDays = new boolean[strFirst.length];
        chooseDayInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        choosenInt.setVisibility(View.VISIBLE);
                        choosenInt.setText(stringBuilder.toString());
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
                            choosenInt.setVisibility(View.VISIBLE);
                            choosenInt.setText("");
                        }
                    }
                });
                builder.show();
            }
        });
        return view;
    }
    public void checkInterval(){
        Bundle bundle = new Bundle();

        String startIntStr = startInt.getText().toString();
        String endIntStr = endInt.getText().toString();
        String choosenStr = choosenInt.getText().toString();
        if(!startIntStr.trim().isEmpty()&&!endIntStr.trim().isEmpty()&&numberOfDosesInt!=0&&!choosenStr.trim().isEmpty()){

            intervalMedication.setNumberOfDoses(numberOfDosesInt);
            intervalMedication.setStartDate(startIntStr);
            intervalMedication.setEndDate(endIntStr);
            intervalMedication.setDays(choosenStr);
            intervalMedication.setStartDateInMillis(convertDateToMillis(startIntStr));
            intervalMedication.setEndDateInMillis(convertDateToMillis(endIntStr));
            bundle.putSerializable("alarm", intervalMedication);
            Navigation.findNavController(view).navigate(R.id.action_intervalFragment_to_setAlarmFragment,bundle);
        }
        else{
            Toast.makeText(getContext(), "please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}