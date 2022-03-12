package com.example.medred.addmedication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.medred.R;


public class AddMedicationPrimary extends Fragment {

    View view;
    //setting drop down list for the unit_med
    String unitMedItem , frequencyItem;
    Spinner unitSpinner , frequencySpinner;

    //declaring rest of components
    EditText medicationNameET,medicationStrengthET;
    Button nextBtn;
    public static int fragmentChoose;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add_medication_primary, container, false);

        //declaring rest of components
        medicationNameET=view.findViewById(R.id.medicationNameET);
        medicationStrengthET=view.findViewById(R.id.medicationStrengthET);
        nextBtn=view.findViewById(R.id.nextBtn);

        //set spinner for unit medication
        unitSpinner=view.findViewById(R.id.unitSpinner);
        ArrayAdapter<CharSequence> arrayAdapterUnit = ArrayAdapter.createFromResource(getContext(),R.array.unitItemSpinner,android.R.layout.simple_spinner_item);
        arrayAdapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(arrayAdapterUnit);
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unitMedItem=adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // set spinner for frequency medication
        frequencySpinner=view.findViewById(R.id.frequencySpinner);
        ArrayAdapter<CharSequence> arrayAdapterFreq = ArrayAdapter.createFromResource(getContext(),R.array.frequencyItemSpinner,android.R.layout.simple_spinner_item);
        arrayAdapterFreq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(arrayAdapterFreq);
        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                frequencyItem=adapterView.getItemAtPosition(i).toString();
                switch (frequencyItem){
                    case "As Needed":
                        fragmentChoose=1;
                        break;
                    case "EveryDay":
                        fragmentChoose=2;
                        break;
                    case"Days Interval":
                        fragmentChoose=3;
                        break;
                    case"Specific Day":
                        fragmentChoose=3;
                        break;
                    default:
                        Toast.makeText(getContext(), "no fragment selected" + frequencyItem, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextClicker();
            }
        });
        return view;
    }

    public void nextClicker() {
        String medName = medicationNameET.getText().toString();
        String medStrength = medicationStrengthET.getText().toString();
        //checking all fields are full
        if (!medName.trim().isEmpty() && !medStrength.trim().isEmpty() && !frequencyItem.isEmpty() && !unitMedItem.isEmpty()) {
            if (fragmentChoose==1) {
               // Toast.makeText(getContext(), "Fisrt Fragment" + frequencyItem, Toast.LENGTH_SHORT).show();
               //addMedicationActivity.changeFragment(new AddMedicationFinal());
                replaceFragment(new AddMedicationFinal());

            } else if (fragmentChoose==2) {
                //Toast.makeText(getContext(), "Second and Third together :" + frequencyItem, Toast.LENGTH_SHORT).show();
                //addMedicationActivity.changeFragment(new EveryDayFragment());
                replaceFragment(new EveryDayFragment());

            } else if (fragmentChoose==3) {
                //Toast.makeText(getContext(), "Fourth fragment" + frequencyItem, Toast.LENGTH_SHORT).show();
                //addMedicationActivity.changeFragment(new IntervalFragment());

                replaceFragment(new IntervalFragment());
            }
        }
        else{
            Toast.makeText(getContext(), "fill all fields please"  , Toast.LENGTH_SHORT).show();
        }



    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}