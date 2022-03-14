package com.example.medred.addmedication.view;

import static com.example.medred.addmedication.view.AddMedicationActivity.medicationMain;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.example.medred.addmedication.presenter.AddMedicationPresenterInterface;
import com.example.medred.addmedication.presenter.MedicationPresenter;
import com.example.medred.db.ConcreteLocalSource;
import com.example.medred.model.Medication;
import com.example.medred.model.Repository;


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


    AddMedicationPresenterInterface addMedicationPresenterInterface;




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
        reasonsET=view.findViewById(R.id.reasonsET);
        addMedicationActivity = new AddMedicationActivity();

        addMedicationPresenterInterface = new MedicationPresenter(this,
                Repository.getInstance(getContext(),null,
                        ConcreteLocalSource.getInstance(getContext())));


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
                checkMedication();
//                insertMovie(medicationMain);
                setMedicationView(medicationMain);
                Log.d("TAG", "onClick:DONE ");



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


        if(constraintFinal.getVisibility() == View.VISIBLE&&!pillStockItem.trim().isEmpty()&&!pillLeftItem.trim().isEmpty()&&!refillStr.trim().isEmpty()){
                Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
            medicationMain.setRefillReminder(true);
            medicationMain.setReason(reasonsETStr);
            medicationMain.setPillStock(pillStockItem);
            medicationMain.setLeftPillReminder(pillLeftItem);
            medicationMain.setAlarmRefillTime(refillStr);
           // insertMedication(medicationMain);

        }
        else if (constraintFinal.getVisibility() == View.INVISIBLE&&!pillStockItem.trim().isEmpty()){
                Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
                medicationMain.setRefillReminder(false);
                medicationMain.setReason(reasonsETStr);
                medicationMain.setPillStock(pillStockItem);
           // insertMedication(medicationMain);


        }
        else{
            Toast.makeText(getContext(), "please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
    public void checkMedication(){
        if(medicationMain==null){
            Toast.makeText(getContext(), "no", Toast.LENGTH_SHORT).show();
        }
        else{
           // Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "checkMedication: "+medicationMain.getName());
            Log.d("TAG", "checkMedication: "+medicationMain.getStrength());
            Log.d("TAG", "checkMedication: "+medicationMain.getUnit());
            Log.d("TAG", "checkMedication: "+medicationMain.getFrequency());
            Log.d("TAG", "checkMedication: "+medicationMain.getNumberOfDoses());
            Log.d("TAG", "checkMedication: "+medicationMain.getStartDate());
            Log.d("TAG", "checkMedication: "+medicationMain.getEndDate());
            Log.d("TAG", "checkMedication: "+medicationMain.getDays());

            if(medicationMain.getFrequency()==2 || medicationMain.getFrequency()==3){
                for(int i = 0 ; i<medicationMain.getSetAlarm().size();i++){
                    Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().get(i).getHour());
                    Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().get(i).getMinute());
                    Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().get(i).getFormat());
                }
//            Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().getHour());
//            Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().getMinute());
//            Log.d("TAG", "checkMedication: "+medicationMain.getSetAlarm().getFormat());
                for(int i = 0 ; i<medicationMain.getPillEachDose().size();i++){
                    Log.d("TAG", "medication doses : "+medicationMain.getPillEachDose().get(i));
                }
            }


            //Log.d("TAG", "checkMedication: "+medicationMain.getPillEachDose());

            Log.d("TAG", "checkMedication: "+medicationMain.getReason());
            Log.d("TAG", "checkMedication: "+medicationMain.getPillStock());
            Log.d("TAG", "checkMedication: "+medicationMain.isRefillReminder());
            Log.d("TAG", "checkMedication: "+medicationMain.getLeftPillReminder());
            Log.d("TAG", "checkMedication: "+medicationMain.getAlarmRefillTime());




        }
    }


    @Override
    public void setMedicationView(Medication medicationModel) {
        addMedicationPresenterInterface.setMedicationPresenter(medicationModel);
        Log.d("TAG", "setMedicationView:DONE DONE ");
    }
}