package com.example.medred.Home.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medred.R;

import com.example.medred.databinding.HomeMedicationItemBinding;
import com.example.medred.model.Alarm;
import com.example.medred.model.Medication;
import com.example.medred.model.Reminders;

import java.util.ArrayList;
import java.util.List;


public class HomeMedicationAdapter extends RecyclerView.Adapter<HomeMedicationAdapter.ViewHolder>{

    private List<Reminders> medicines;
    public OnHomeMedicationClickListener listener;

    public HomeMedicationAdapter(List<Reminders> medicines, OnHomeMedicationClickListener listener){
        this.medicines = medicines;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(HomeMedicationItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Reminders medicine = medicines.get(position);
        Log.i("TAG",medicine.getName()+"adapter");
        for (int i = 0; i < medicine.getSetAlarm().size(); i++) {

            Alarm alarm = medicine.getSetAlarm().get(i);
            Log.i("TAG",alarm.getHour()+""+alarm.getMinute()+""+alarm.getFormat());
        }


       holder.binding.timeTxt.setText(medicine.getSetAlarm().get(0).getHour()+" : "
               +medicine.getSetAlarm().get(0).getMinute()+ "  "+medicine.getSetAlarm().get(0).getFormat());
        holder.binding.tvMedicationName.setText(medicine.getName());

        holder.binding.tvMedicationDose.setText(medicine.getStrength()
                + " " + medicine.getUnit()+"   take "+medicine.getNumberOfDoses()+" pills");

        if(medicine.getImageID() == 0){
            holder.binding.ivMedicationIcon.setImageResource(R.drawable.ic_baseline_medical_services_24);
        }else{
            holder.binding.ivMedicationIcon.setImageResource(medicine.getImageID());
        }


      /*  holder.binding.btnMedicationRemove.setOnClickListener(view -> {
            listener.onDelete(medicine);
          //  notifyDataSetChanged();
        });*/

        holder.binding.cvHomeMedicationItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","onclickkkkkkkkkk");
                listener.onClick(medicine.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public void setMedications(List<Reminders> medicines){
        this.medicines = medicines;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        HomeMedicationItemBinding binding;
        public ViewHolder(@NonNull HomeMedicationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
