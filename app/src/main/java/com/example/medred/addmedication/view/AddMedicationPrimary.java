package com.example.medred.addmedication.view;

import static com.example.medred.addmedication.view.AddMedicationActivity.medicationMain;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.medred.Home.view.HomeActivity;
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
    ImageView medicationDrop,medicationBottle,medicationPill,medicationInjection,medicationRespire;
    static int imageNumber;
    AddMedicationPrimary addMedicationPrimary;
    Dialog dialogBack;
    Button yesBackBtn , noBackBtn;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add_medication_primary, container, false);

        //declaring rest of components
        medicationNameET=view.findViewById(R.id.medicationNameET);
        medicationStrengthET=view.findViewById(R.id.medicationStrengthET);
        nextBtn=view.findViewById(R.id.nextBtn);

        //declaring imageviews
        medicationBottle=view.findViewById(R.id.medicationBottle);
        medicationPill=view.findViewById(R.id.medicationPill);
        medicationDrop=view.findViewById(R.id.medicationDrop);
        medicationInjection=view.findViewById(R.id.medicationInjection);
        medicationRespire=view.findViewById(R.id.medicationRespire);

        //onclick listener for image
        medicationBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageNumber=1;

            }
        });

        medicationPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageNumber=2;
            }
        });
        medicationDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageNumber=3;
            }
        });
        medicationInjection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageNumber=4;
            }
        });
        medicationRespire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageNumber=5;
            }
        });


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




    @Override
    public void onResume() {
        super.onResume();

        //handle onBackPressed
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    dialogBack =new Dialog(getContext());


                    dialogBack.setContentView(R.layout.medication_onbackpressed);
                    dialogBack.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialogBack.setCancelable(false);

                    yesBackBtn=dialogBack.findViewById(R.id.yesBackBtn);
                    yesBackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogBack.dismiss();
                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                    });
                    noBackBtn=dialogBack.findViewById(R.id.noBackBtn);
                    noBackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogBack.dismiss();
                        }
                    });
                    Log.d("TAG", "onKey: hena aho");

                    return true;
                }
                return false;
            }
        });











    }

    public void nextClicker() {
        Medication medicationPrimary = new Medication();
        Bundle bundle = new Bundle();

        String medName = medicationNameET.getText().toString();
        String medStrength = medicationStrengthET.getText().toString();

        //setimages
        switch(imageNumber){
            case 1:
                medicationPrimary.setImageID(R.drawable.ic_medication_bottle);
                break;
            case 2:
                medicationPrimary.setImageID(R.drawable.ic_medication_pill);
                break;
            case 3:
                medicationPrimary.setImageID(R.drawable.ic_medication_eyedrop);
                break;
            case 4:
                medicationPrimary.setImageID(R.drawable.ic_medication_injection);
                break;
            case 5:
                medicationPrimary.setImageID(R.drawable.ic_medication_respire);
                break;
            default:
                medicationPrimary.setImageID(0);
        }




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