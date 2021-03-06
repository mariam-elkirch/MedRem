package com.example.medred.addmedication.view;

import static com.example.medred.addmedication.view.AddMedicationActivity.medicationMain;
import static com.example.medred.addmedication.view.SetAlarmFragment.alarmMedication;

import android.app.TimePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.medred.R;
import com.example.medred.model.Alarm;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    ArrayList<Alarm> alarm;
    String format;
    public static String time;
    public static String pillStr;
    public static String numDoses;
    public static int numD;
    int selectedHour, selectedMinute;
    ArrayList<String> arrayListNumber= new ArrayList<String>();
    Alarm alarmItem;
    ArrayList<Alarm> arrayListAlarm= new ArrayList<Alarm>();
    int numbers;
    static int counterTrial=0;

    public AlarmAdapter() {
    }

    public void setData(ArrayList<Alarm> alarm) {
        this.alarm = alarm;
        notifyDataSetChanged();
    }

    public void getNumber(int numbers){
        this.numbers=numbers;
        notifyDataSetChanged();
    }
    ArrayList<String> arrayListNumberOrg= new ArrayList<String>(numbers);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.alarm_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.showAlarmTV.setOnClickListener(new View.OnClickListener() {
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
                        holder.alarmSetTV.setText(selectedHour+":"+selectedMinute+":"+format);
                        holder.alarmSetTV.setVisibility(View.VISIBLE);
                        time = holder.alarmSetTV.getText().toString();
                        //counterTrial=counterTrial+1;
                        Log.d("TAG", "onTimeSet: trial number"+counterTrial);
                        alarmItem= new Alarm(selectedHour,selectedMinute,format);
                        arrayListAlarm.add(alarmItem);
                        holder.numberPillsED.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                numDoses= holder.numberPillsED.getText().toString();
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                arrayListNumber.add(numDoses);
                            }

                        });
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), TimePickerDialog.THEME_HOLO_LIGHT, onTimeSetListener,selectedHour,selectedMinute,false);
                timePickerDialog.setTitle("Set Alarm");
                timePickerDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return alarm.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        EditText numberPillsED;
        TextView alarmSetTV , showAlarmTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmSetTV=itemView.findViewById(R.id.alaramSetTV);
            showAlarmTV=itemView.findViewById(R.id.showAlarmTV);
            numberPillsED=itemView.findViewById(R.id.numberPillsED);
        }

        public EditText getNumberPillsED() {
            return numberPillsED;
        }

        public TextView getAlarmSetTV() {
            return alarmSetTV;
        }

        public TextView getShowAlarmTV() {
            return showAlarmTV;
        }
    }

    //check the fields
    public int alarmCheck(){
        int reInt=0;
        Log.d("TAG", "alarmCheck: "+arrayListNumberOrg.size());
        Log.d("TAG", "alarmCheck: "+arrayListNumber.size());
//here is original down
    if(numDoses!=null&&!numDoses.trim().isEmpty()&& !time.trim().isEmpty()){
        //&&counterTrial==numbers
        reInt=1;
        for(int i = 0 ; i < arrayListAlarm.size();i++){
            Log.d("TAG", "alarmCheck: "+arrayListAlarm.get(i).getHour());
            alarmMedication.setSetAlarm(arrayListAlarm);
        }
        for(int i = 0 ; i < arrayListNumber.size();i++){
            Log.d("TAG", "item number is:"+ arrayListNumber.get(i));
            alarmMedication.setPillEachDose(arrayListNumber);
        }
    }
        return reInt;
    }

}




