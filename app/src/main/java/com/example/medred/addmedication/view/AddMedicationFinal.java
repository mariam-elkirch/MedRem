package com.example.medred.addmedication.view;

import static com.example.medred.addmedication.view.AddMedicationActivity.medicationMain;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.medred.Home.view.HomeActivity;
import com.example.medred.MainActivity;
import com.example.medred.R;
import com.example.medred.addmedication.presenter.AddMedicationPresenterInterface;
import com.example.medred.addmedication.presenter.MedicationPresenter;
import com.example.medred.db.ConcreteLocalSource;
import com.example.medred.db.MedicationDAO;
import com.example.medred.model.Medication;
import com.example.medred.model.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class AddMedicationFinal extends Fragment implements AddMedicationViewInterface {
    Spinner reasonSpinner;
    String itemReason;
    View view;
    AddMedicationActivity addMedicationActivity;
    CheckBox refillReminderCheckBox;
    EditText pillStockET, pillLeftEt,reasonsET;

    ConstraintLayout constraintFinal;
    String  pillStockItem , pillLeftItem;
    Button doneBtn;
    TimePicker refillTimePicker;
    int hour , minute;

    TextView refillReminderTV , dateRefill;
    DatePickerDialog picker;
    int selectedHour, selectedMinute;
    String format;
    String reasonsETStr;
    Dialog dialogSucess;
    Button doneSucessBtn;


    AddMedicationPresenterInterface addMedicationPresenterInterface;
    Medication finalMedication = new Medication();


    //try firebase
    private FirebaseUser user;
    private DatabaseReference reference , databaseReference;
    private String userId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add_medication_final, container, false);

        //try firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("user");
        userId=user.getUid();
        //Log.d("TAG", "onCreateView: "+user+userId);
        //databaseReference=FirebaseDatabase.getInstance().getReference(userId);


        //sucess dialog
        dialogSucess =new Dialog(getContext());


            dialogSucess.setContentView(R.layout.medication_sucess_layout);
            dialogSucess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialogSucess.setCancelable(false);


        doneSucessBtn=dialogSucess.findViewById(R.id.doneSucessBtn);
        doneSucessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSucess.dismiss();
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });


        // set all ids for the components
        pillLeftEt=view.findViewById(R.id.pillLeftET);
        pillStockET=view.findViewById(R.id.pillStockET);
        refillReminderCheckBox=view.findViewById(R.id.refillReminderCheckBox);
        constraintFinal=view.findViewById(R.id.constraintFinal);
        doneBtn=view.findViewById(R.id.doneBtn);
        refillReminderTV=view.findViewById(R.id.refillReminderTV);
        dateRefill=view.findViewById(R.id.dateRefill);
        reasonsET=view.findViewById(R.id.reasonsET);
        addMedicationActivity = new AddMedicationActivity();

        addMedicationPresenterInterface = new MedicationPresenter(this, Repository.getInstance(getContext(),null, ConcreteLocalSource.getInstance(getContext())));


        //get bundle
        //get bundle
        Bundle bundle = getArguments();
        Medication receiveMedication= (Medication) bundle.getSerializable("final");
        finalMedication=receiveMedication;

//        Log.d("TAG", "onCreateView: "+receiveMedication.getName());
//        Log.d("TAG", "onCreateView: "+receiveMedication.getFrequency());
//        Log.d("TAG", "onCreateView: "+receiveMedication.getStrength());
//        Log.d("TAG", "onCreateView: "+receiveMedication.getUnit());
//
//        finalMedication.setName(receiveMedication.getName());
//        finalMedication.setFrequency(receiveMedication.getFrequency());
//        finalMedication.setUnit(receiveMedication.getUnit());
//        finalMedication.setStrength(receiveMedication.getStrength());




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
                        dateRefill.setText(selectedHour+":"+selectedMinute+":"+format);
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
                //checkMedication();
//                insertMovie(medicationMain);
 //               setMedicationView(finalMedication);
//                Log.d("TAG", "onClick:DONE ");



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
        reasonsETStr=reasonsET.getText().toString();
        finalMedication.setActive(true);



        if(constraintFinal.getVisibility() == View.VISIBLE&&!pillStockItem.trim().isEmpty()&&!pillLeftItem.trim().isEmpty()&&!refillStr.trim().isEmpty()){
//            int numberStock = Integer.parseInt(pillStockItem);
//            int numberLeft = Integer.parseInt(pillLeftItem);
//                medicationMain.setRefillReminder(true);
//                medicationMain.setReason(reasonsETStr);
//                medicationMain.setPillStock(pillStockItem);
//                medicationMain.setLeftPillReminder(pillLeftItem);
//                medicationMain.setAlarmRefillTime(refillStr);

                //try 17/3 bundle
            finalMedication.setRefillReminder(true);
            finalMedication.setReason(reasonsETStr);
            finalMedication.setPillStock(pillStockItem);
            finalMedication.setLeftPillReminder(pillLeftItem);
            finalMedication.setAlarmRefillTime(refillStr);

            //try bundle insert in room
            setMedicationView(finalMedication);
            Log.d("TAG", "onClick:DONE ");

            //try firebase

            reference.child(userId).child("medication").child(finalMedication.getName()).setValue(finalMedication);
//            String id = String.valueOf(finalMedication.getId());
//            Log.d("TAG", "handleFinalFragment: ID "+id);






            //mein insert here in room
                //try
//            setMedicationView(medicationMain);
//            Log.d("TAG", "onClick:DONE ");



                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
                //Toast.makeText(getContext(), "Medication added successfully!", Toast.LENGTH_SHORT).show();
               // Log.d("TAG", "handleFinalFragment: "+medicationMain.getName());
            dialogSucess.show();

           // insertMedication(medicationMain);



        }
        else if (constraintFinal.getVisibility() == View.INVISIBLE&&!pillStockItem.trim().isEmpty()){
                Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
//                medicationMain.setRefillReminder(false);
//                medicationMain.setReason(reasonsETStr);
//                medicationMain.setPillStock(pillStockItem);

                //try bundle 17/3
            finalMedication.setRefillReminder(false);
            finalMedication.setReason(reasonsETStr);
            finalMedication.setPillStock(pillStockItem);




                //insert by bundle 17/3
            setMedicationView(finalMedication);
            Log.d("TAG", "onClick:DONE ");

            reference.child(userId).child("medication").child(finalMedication.getName()).setValue(finalMedication);
//            String id = String.valueOf(finalMedication.getId());
//            Log.d("TAG", "handleFinalFragment:ID "+id);



            // insertMedication(medicationMain);
            //try
            //main insert in room here commented
//            setMedicationView(medicationMain);
//            Log.d("TAG", "onClick:DONE ");



            Intent intent = new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
//
//            Toast.makeText(getContext(), "Medication added successfully!", Toast.LENGTH_SHORT).show();
            //Log.d("TAG", "handleFinalFragment: "+medicationMain.getName());

            dialogSucess.show();

        }
        else{
            Toast.makeText(getContext(), "please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
//    public void checkMedication(){
//        if(medicationMain==null){
//            Toast.makeText(getContext(), "no", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Log.d("TAG", "checkMedication: "+medicationMain.getName());
//            Log.d("TAG", "checkMedication: "+medicationMain.getStrength());
//            Log.d("TAG", "checkMedication: "+medicationMain.getUnit());
//            Log.d("TAG", "checkMedication: "+medicationMain.getFrequency());
//            Log.d("TAG", "checkMedication: "+medicationMain.getNumberOfDoses());
//            Log.d("TAG", "checkMedication: "+medicationMain.getStartDate());
//            Log.d("TAG", "checkMedication: "+medicationMain.getEndDate());
//            Log.d("TAG", "checkMedication: "+medicationMain.getDays());
//
//            if(medicationMain.getFrequency()==2 || medicationMain.getFrequency()==3){
//                for(int i = 0 ; i<medicationMain.getSetAlarm().size();i++){
//                    Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().get(i).getHour());
//                    Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().get(i).getMinute());
//                    Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().get(i).getFormat());
//                }
////            Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().getHour());
////            Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().getMinute());
////            Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().getFormat());
//                for(int i = 0 ; i<medicationMain.getPillEachDose().size();i++){
//                    Log.d("TAG", "medication doses : "+medicationMain.getPillEachDose().get(i));
//                }}
//
//
//        }
//    }

//di bet3t el room matensich w temsa7iha
    @Override
    public void setMedicationView(Medication medicationModel) {
        addMedicationPresenterInterface.setMedicationPresenter(medicationModel);
        Log.d("TAG", "setMedicationView:DONE DONE ");
    }

    @Override
    public LiveData<Medication> getShowMed(int idMed) {
        return null;
    }

    @Override
    public void updateMedication(Medication medicationModel) {

    }


}