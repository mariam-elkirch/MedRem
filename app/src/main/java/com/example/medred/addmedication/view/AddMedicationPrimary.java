package com.example.medred.addmedication.view;

import static com.example.medred.addmedication.view.AddMedicationActivity.medicationMain;

import android.os.Bundle;
import android.util.Log;
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
import androidx.navigation.Navigation;

import com.example.medred.R;
import com.example.medred.model.Medication;

import java.util.ArrayList;


public class AddMedicationPrimary extends Fragment {

    View view;
    //setting drop down list for the unit_med
    String unitMedItem , frequencyItem;
    Spinner unitSpinner , frequencySpinner;

    //declaring rest of components
    EditText medicationNameET,medicationStrengthET;
    Button nextBtn;
    public static int fragmentChoose;
    AddMedicationActivity addMedicationActivity;


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
                switch (unitMedItem){
//                    case "Choose Unit":
//                        Toast.makeText(getContext(), "no fragment selected" + unitMedItem, Toast.LENGTH_SHORT).show();
//                        break;
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
//                    case "Choose Frequency":
//                        Toast.makeText(getContext(), "no fragment selected" + frequencyItem, Toast.LENGTH_SHORT).show();
//                        break;
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
        Medication medicationPrimary = new Medication();
        Bundle bundle = new Bundle();

        String medName = medicationNameET.getText().toString();
        String medStrength = medicationStrengthET.getText().toString();
        //checking all fields are full
        if (!medName.trim().isEmpty() && !medStrength.trim().isEmpty() && !frequencyItem.isEmpty() && !unitMedItem.isEmpty()) {
            medicationPrimary.setName(medName);
            medicationPrimary.setStrength(medStrength);
            medicationPrimary.setUnit(unitMedItem);

            if (fragmentChoose==1) {
                medicationPrimary.setFrequency(fragmentChoose);
                bundle.putSerializable("final", medicationPrimary);
                Navigation.findNavController(view).navigate(R.id.action_addMedicationPrimary_to_addMedicationFinal,bundle);

            } else if (fragmentChoose==2) {
                //Navigation.findNavController(view).navigate(R.id.action_addMedicationPrimary_to_everyDayFragment);
                //medicationMain.setFrequency(fragmentChoose);
                medicationPrimary.setFrequency(fragmentChoose);
                medicationPrimary.setDays("EveryDay");
                bundle.putSerializable("everyday", medicationPrimary);
                Navigation.findNavController(view).navigate(R.id.action_addMedicationPrimary_to_everyDayFragment,bundle);

            } else if (fragmentChoose==3) {
               // Navigation.findNavController(view).navigate(R.id.action_addMedicationPrimary_to_intervalFragment);
               // medicationMain.setFrequency(fragmentChoose);
                medicationPrimary.setFrequency(fragmentChoose);
                bundle.putSerializable("interval", medicationPrimary);
                Navigation.findNavController(view).navigate(R.id.action_addMedicationPrimary_to_intervalFragment,bundle);

            }
//            medicationMain.setName(medName);
//            medicationMain.setStrength(medStrength);
//            medicationMain.setUnit(unitMedItem);

            //try
//            FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            AddMedicationFinal fragment2 = new AddMedicationFinal();
//
//            Bundle bundle = new Bundle();
//            //YourObj obj = SET_YOUR_OBJECT_HERE;
//            //Medication medicationPrimary = new Medication();
//            medicationPrimary.setName(medName);
//            medicationPrimary.setStrength(medStrength);
//            medicationPrimary.setUnit(unitMedItem);
//            medicationPrimary.setFrequency(fragmentChoose);
//
//
//
//            bundle.putSerializable("PrimaryMed", medicationPrimary);
//            fragment2.setArguments(bundle);
//            ft.replace(android.R.id.content, fragment2);
//            ft.addToBackStack(null);
//            ft.commit();


        }
        else{
            Toast.makeText(getContext(), "fill all fields please"  , Toast.LENGTH_SHORT).show();
        }



    }




}