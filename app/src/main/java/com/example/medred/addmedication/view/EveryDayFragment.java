package com.example.medred.addmedication.view;

import static com.example.medred.addmedication.view.AddMedicationActivity.medicationMain;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;


import com.example.medred.R;
import com.example.medred.model.Medication;

import java.util.Calendar;

public class EveryDayFragment extends Fragment {
    AddMedicationActivity addMedicationActivity;
    View view;

    Button nextBtnEveryDay;
    TextView startDateClickEV, endDateClickEV, setStartEV, setEndEV;
    DatePickerDialog picker;
    Spinner doseSpinnerEV;
    String itemDoseEV;
    public static int numberOfDoseEV;
    SetAlarmFragment setAlarmFragment;
    Medication everydayMedication = new Medication();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_every_day, container, false);
        //receive bundle
        Bundle bundle = getArguments();
        Medication receiveMedication= (Medication) bundle.getSerializable("everyday");
        everydayMedication=receiveMedication;

//        Log.d("TAG", "onCreateView: "+receiveMedication.getName());
//        Log.d("TAG", "onCreateView: "+receiveMedication.getFrequency());
//        Log.d("TAG", "onCreateView: "+receiveMedication.getStrength());
//        Log.d("TAG", "onCreateView: "+receiveMedication.getUnit());

//        everydayMedication.setName(receiveMedication.getName());
//        everydayMedication.setFrequency(receiveMedication.getFrequency());
//        everydayMedication.setUnit(receiveMedication.getUnit());
//        everydayMedication.setStrength(receiveMedication.getStrength());



        //Log.d("TAG", "onCreateView: "+everydayMedication.getName());




        nextBtnEveryDay = view.findViewById(R.id.nextBtnEveryDay);

        startDateClickEV = view.findViewById(R.id.startDateClickEV);
        endDateClickEV = view.findViewById(R.id.endDateClickEV);
        setStartEV = view.findViewById(R.id.setStartEV);
        setEndEV = view.findViewById(R.id.setEndEV);

        startDateClickEV.setOnClickListener(new View.OnClickListener() {
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
                                setStartEV.setText(str);
                            }
                        }, year, month, day);
                picker.show();
                setStartEV.setVisibility(View.VISIBLE);
            }
        });

        endDateClickEV.setOnClickListener(new View.OnClickListener() {
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
                                setEndEV.setText(str);
                            }
                        }, year, month, day);
                picker.show();
                setEndEV.setVisibility(View.VISIBLE);
            }
        });

        //spinner
        doseSpinnerEV = view.findViewById(R.id.doseSpinnerEV);
        ArrayAdapter<CharSequence> arrAdapterCycle = ArrayAdapter.createFromResource(getContext(), R.array.doseItemSpinner, android.R.layout.simple_spinner_item);

        arrAdapterCycle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doseSpinnerEV.setAdapter(arrAdapterCycle);
        doseSpinnerEV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemDoseEV = adapterView.getItemAtPosition(i).toString();
                // Toast.makeText(getContext(), "reason selected is:"+ itemReason, Toast.LENGTH_SHORT).show();
                switch (itemDoseEV) {
                    case "Choose Dose":
                        Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                        break;
                    case "1":
                        numberOfDoseEV = 1;
                        break;
                    case "2":
                        numberOfDoseEV = 2;
                        break;
                    case "3":
                        numberOfDoseEV = 3;
                        break;
                    case "4":
                        numberOfDoseEV = 4;
                        break;
                    case "5":
                        numberOfDoseEV = 5;
                        break;
                    case "6":
                        numberOfDoseEV = 6;
                        break;
                    default:
                        numberOfDoseEV = 0;
                        Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        nextBtnEveryDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });


        return view;
    }

    public void showDate() {
        Bundle bundle = new Bundle();

        String setStartStr = setStartEV.getText().toString();
        String setEndStr = setEndEV.getText().toString();
        if (!setStartStr.trim().isEmpty() && !setEndStr.trim().isEmpty() && numberOfDoseEV != 0) {
            //replaceFragment(new SetAlarmFragment(numberOfDoseEV));
            //Toast.makeText(getContext(), "num is" + numberOfDoseEV, Toast.LENGTH_SHORT).show();
           // Navigation.findNavController(view).navigate(R.id.action_everyDayFragment_to_setAlarmFragment);
//            medicationMain.setNumberOfDoses(numberOfDoseEV);
//            medicationMain.setStartDate(setStartStr);
//            medicationMain.setEndDate(setEndStr);
//            replaceFragment(new SetAlarmFragment(numberOfDoseEV));

            everydayMedication.setNumberOfDoses(numberOfDoseEV);
            everydayMedication.setStartDate(setStartStr);
            everydayMedication.setEndDate(setEndStr);
            bundle.putSerializable("alarm", everydayMedication);
            Navigation.findNavController(view).navigate(R.id.action_everyDayFragment_to_setAlarmFragment,bundle);











        } else {
            Toast.makeText(getContext(), "please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

        public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }



}


