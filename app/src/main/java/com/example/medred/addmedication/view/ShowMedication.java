package com.example.medred.addmedication.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Database;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medred.Home.view.HomeActivity;
import com.example.medred.MainActivity;
import com.example.medred.R;
import com.example.medred.addmedication.presenter.AddMedicationPresenterInterface;
import com.example.medred.addmedication.presenter.MedicationPresenter;
import com.example.medred.db.ConcreteLocalSource;
import com.example.medred.db.LocalSource;
import com.example.medred.db.MedicationDAO;
import com.example.medred.medicationsList.view.MedicationsListFragment;
import com.example.medred.model.Alarm;
import com.example.medred.model.Medication;
import com.example.medred.model.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ShowMedication extends Fragment implements AddMedicationViewInterface {

    View view;
    TextView stateShowMed , nameShowMed ,reminderShowMed,pillsLeftShowMed,pillsStockShowMed;
    ImageView showMedImg;
    Button updateStockBtn ,addDoseBtn ;
    TextView stateBtn,editTV;
    Button editBtn;

    static int ID;
    Medication medicationShowItem;
    MedicationDAO medicationDAO;

    AddMedicationPresenterInterface addMedicationPresenterInterface;
    String reminderStr , doseStr;
    Dialog dialogAddDose ,dialogStock;
    //dialog item
    Button cancelAddBtn, doneAddBtn , cancelStockBtn, doneStockBtn;
    TextView showClockTV , clockSetTV;
    EditText doseED , stockED;
    int pHour, pMinute;
    String format,dose,time,stock;
    Alarm alarmItem;
    private FirebaseUser user;
    private DatabaseReference reference , databaseReference;
    private String userId;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_show_medication, container, false);
        //setting id
        stateShowMed = view.findViewById(R.id.stateShowMed);
        nameShowMed = view.findViewById(R.id.nameShowMed);
        reminderShowMed = view.findViewById(R.id.reminderShowMed);
        pillsLeftShowMed = view.findViewById(R.id.pillsLeftShowMed);
        pillsStockShowMed = view.findViewById(R.id.pillsStockShowMed);
        showMedImg=view.findViewById(R.id.showMedImg);
        updateStockBtn=view.findViewById(R.id.updateStockBtn);
        addDoseBtn=view.findViewById(R.id.addDoseBtn);
        stateBtn=view.findViewById(R.id.stateBtn);
        editTV=view.findViewById(R.id.editTV);
        //editBtn=view.findViewById(R.id.editBtn);
        //getting bundle

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ID = bundle.getInt("ID");
        }

        //setting data
        medicationShowItem = new Medication();
        addMedicationPresenterInterface = new MedicationPresenter(this, Repository.getInstance(getContext(),null, ConcreteLocalSource.getInstance(getContext())));
        getMedicine(ID);

        //end of dialog setting
        addDoseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddDose.show();
            }
        });




        return view;
    }

    public void getMedicine(int id){
        LiveData<Medication> medicine = getShowMed(id);
        medicine.observe(this, new Observer<Medication>() {
            @Override
            public void onChanged(Medication medication) {
                //Log.d("TAG", "getMedicine:  okay it is here " + medication.getName());
                showMedication(medication);

               //
            }
        });
    }

//    private void updateMedicationShort(Medication medication) {
//        ArrayList<Alarm> arrayListA = medication.getSetAlarm();
//        ArrayList<String>arrayListD = medication.getPillEachDose();
//        int size = medication.getSetAlarm().size()+1;
//        arrayListA.add(size,alarmItem);
//        medication.setSetAlarm(arrayListA);
//        arrayListD.add(size,dose);
//        medication.setPillEachDose(arrayListD);
//    }

    //show the received object
    void showMedication(Medication medication){
        if(medication.isActive()){
            stateShowMed.setText("Active");
            stateBtn.setText("Suspend");
        }else{
            stateShowMed.setText("InActive");
            stateBtn.setText("Activate");
        }
        showMedImg.setImageResource(medication.getImageID());
        nameShowMed.setText(medication.getName());
        if(medication.isRefillReminder()){
        pillsLeftShowMed.setText(medication.getLeftPillReminder()+" Pills Left");
        pillsStockShowMed.setText("Refill Reminder When I Have "+medication.getPillStock()+" Pills");}
        else{
            pillsLeftShowMed.setText(" No Reminder Is Set");
            pillsStockShowMed.setText(" No Reminder Is Set");
        }
        ArrayList<Alarm> arrayListAlarm = medication.getSetAlarm();
        ArrayList<String>arrayListDose = medication.getPillEachDose();
        if(arrayListAlarm==null||arrayListDose==null){
            reminderShowMed.append("No Reminder Is Set");
        }
        else{
            for(int i = 0 ; i < arrayListAlarm.size()&& i < arrayListDose.size();i++){
                Log.d("TAG", "showMedication: "+arrayListAlarm.get(i).getHour()+":"+arrayListAlarm.get(i).getMinute()+":"+arrayListAlarm.get(i).getFormat());
                Log.d("TAG", "showMedication: "+arrayListDose.get(i));
                reminderStr=arrayListAlarm.get(i).getHour()+":"+arrayListAlarm.get(i).getMinute()+":"+arrayListAlarm.get(i).getFormat();
                doseStr=arrayListDose.get(i);
                reminderShowMed.append(reminderStr+" Dose: "+doseStr);
                reminderShowMed.append("\n");
                reminderShowMed.append("\n");
            }
        }


        //try

        //set dialog for add dose
        dialogAddDose =new Dialog(getContext());


        dialogAddDose.setContentView(R.layout.add_dose);
        dialogAddDose.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogAddDose.setCancelable(false);

        cancelAddBtn=dialogAddDose.findViewById(R.id.cancelAddBtn);
        doneAddBtn=dialogAddDose.findViewById(R.id.doneAddBtn);
        showClockTV=dialogAddDose.findViewById(R.id.showclockTV);
        clockSetTV=dialogAddDose.findViewById(R.id.clockSetTV);
        doseED=dialogAddDose.findViewById(R.id.doseED);

        showClockTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        pHour= hour;
                        pMinute=minute;
                        if (pHour == 0) {
                            pHour += 12;
                            format = "AM";
                        } else if (pHour == 12) {
                            format = "PM";
                        } else if (hour > 12) {
                            pHour -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }

                        clockSetTV.setText(pHour+":"+pMinute+":"+format);
                        clockSetTV.setVisibility(View.VISIBLE);
                        time=clockSetTV.getText().toString();
                        alarmItem = new Alarm(pHour,pMinute,format);

                        doseED.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                dose=doseED.getText().toString();
                            }
                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                dose=doseED.getText().toString();
                            }
                            @Override
                            public void afterTextChanged(Editable editable) {
                                dose=doseED.getText().toString();
                            }
                        });
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), TimePickerDialog.THEME_HOLO_LIGHT, onTimeSetListener,pHour,pMinute,false);
                timePickerDialog.setTitle("Set Alarm");
                timePickerDialog.show();
            }
        });

        cancelAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddDose.dismiss();
            }
        });
        doneAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!dose.isEmpty()&&!time.isEmpty()){
                    //code here
                    ArrayList<Alarm> arrayListA = medication.getSetAlarm();
                    ArrayList<String>arrayListD = medication.getPillEachDose();
                    arrayListA.add(alarmItem);
                    arrayListD.add(dose);
//                    Log.d("TAG", "onClick: hereeeee"+alarmItem.getHour());
//                    Log.d("TAG", "onClick: hereeeee"+alarmItem.getMinute());
//                    Log.d("TAG", "onClick: hereeeee"+alarmItem.getFormat());
//                    Log.d("TAG", "onClick: hereeeee"+dose);

                    medication.setPillEachDose(arrayListD);
                    medication.setSetAlarm(arrayListA);
                    updateMedication(medication);
                    //update in firebase
                    //try firebase
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    reference= FirebaseDatabase.getInstance().getReference("user");
                    userId=user.getUid();
                    reference.child(userId).child("medication").child(medication.getName()).setValue(medication);
                    Toast.makeText(getContext(), "Dose is Added successfully", Toast.LENGTH_SHORT).show();
                    dialogAddDose.dismiss();
                    //showMedication(medication);
                    Intent intent = new Intent(getContext(),HomeActivity.class);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getContext(), "please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


        stateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(medication.isActive()){
                    medication.setActive(false);
                }
                else{
                    medication.setActive(true);
                }
                updateMedication(medication);
                //update in firebase
                //try firebase
                user = FirebaseAuth.getInstance().getCurrentUser();
                reference= FirebaseDatabase.getInstance().getReference("user");
                userId=user.getUid();
                reference.child(userId).child("medication").child(medication.getName()).setValue(medication);
                Toast.makeText(getContext(), "State is changed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),HomeActivity.class);
                startActivity(intent);
            }
        });





        dialogStock =new Dialog(getContext());
        dialogStock.setContentView(R.layout.update_stock);
        dialogStock.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogStock.setCancelable(false);

        stockED=dialogStock.findViewById(R.id.stockED);
        cancelStockBtn=dialogStock.findViewById(R.id.cancelStockBtn);
        doneStockBtn=dialogStock.findViewById(R.id.doneStockBtn);

        doneStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stock=stockED.getText().toString();
                if(!stock.isEmpty()){
                   int numberStock= Integer.parseInt(medication.getPillStock());
                   int numberStockAdded = Integer.parseInt(stock)+numberStock;
                   String stockOrigin = String.valueOf(numberStockAdded);
                   medication.setPillStock(stockOrigin);
                   updateMedication(medication);
                   //update in firebase
                    //try firebase
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    reference= FirebaseDatabase.getInstance().getReference("user");
                    userId=user.getUid();
                    reference.child(userId).child("medication").child(medication.getName()).setValue(medication);


                    dialogStock.dismiss();
                    Toast.makeText(getContext(), "Stock Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        cancelStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogStock.dismiss();
            }
        });


        updateStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogStock.show();
            }
        });


        editTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("edit", medication);
//                Navigation.findNavController(view).navigate(R.id.action_showMedication_to_editMedication);
//                // Log.d("TAG", "onClick: "+medication.getName());
                replaceFragment(bundle);

            }
        });


    }


    @Override
    public void setMedicationView(Medication medicationModel) {
        addMedicationPresenterInterface.setMedicationPresenter(medicationModel);
    }

    @Override
    public LiveData<Medication> getShowMed(int idMed) {
        return addMedicationPresenterInterface.getShowMed(idMed);
    }

    @Override
    public void updateMedication(Medication medicationModel) {
        addMedicationPresenterInterface.updateMedication(medicationModel);
    }

    public void replaceFragment( Bundle bundle) {
        EditMedication editMedication = new EditMedication();
        editMedication.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView2, editMedication);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}