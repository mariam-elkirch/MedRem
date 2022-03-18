package com.example.medred.medicationsList.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.medred.R;
import com.example.medred.databinding.MedicationItemBinding;
import com.example.medred.model.Medication;
import java.util.List;


public class MedicationsListAdapter extends RecyclerView.Adapter<MedicationsListAdapter.ViewHolder>{

    private List<Medication> medicines;
    public OnMedicationClickListener listener;
    public MedicationsListAdapter(List<Medication> medicines, OnMedicationClickListener listener){
        this.medicines = medicines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MedicationItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medication medicine = medicines.get(position);
        holder.binding.tvMedicationName.setText(medicine.getName());
        holder.binding.tvMedicationDose.setText(medicine.getStrength()
                + " " + medicine.getUnit());
        holder.binding.tvMedicationLeft.setText(medicine.getPillStock() + " left");
        if(medicine.getImageID() == 0){
            holder.binding.ivMedicationIcon.setImageResource(R.drawable.ic_baseline_medical_services_24);
        }else{
            holder.binding.ivMedicationIcon.setImageResource(medicine.getImageID());
        }
        holder.binding.btnMedicationEdit.setOnClickListener(view -> {
            listener.onEdit(medicine.getId());
        });

        holder.binding.btnMedicationRemove.setOnClickListener(view -> {
            listener.onDelete(medicine);
            notifyDataSetChanged();
        });

        holder.binding.cvMedicationItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(medicine.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public void setMedications(List<Medication> medicines){
        this.medicines = medicines;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        MedicationItemBinding binding;
        public ViewHolder(MedicationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
