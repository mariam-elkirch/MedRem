package com.example.medred.healthtakerslist.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.medred.R;
import com.example.medred.databinding.HealthTakerItemBinding;
import com.example.medred.model.HealthTaker;
import java.util.List;


public class HealthTakersListAdapter extends RecyclerView.Adapter<HealthTakersListAdapter.ViewHolder>{

    private List<HealthTaker> healthTakers;
    private OnHealthTakerClickListener listener;

    public HealthTakersListAdapter(List<HealthTaker> healthTakers, OnHealthTakerClickListener listener){
        this.healthTakers = healthTakers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(HealthTakerItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HealthTaker healthTaker = healthTakers.get(position);
        holder.binding.tvHealthTakerName.setText(healthTaker.getName());
        holder.binding.tvHealthTakerEmail.setText(healthTaker.getEmail());

        if(healthTaker.getImgURL() == null){
            holder.binding.ivHealthTakerImg.setImageResource(R.drawable.ic_userr);
        }else{
            Glide.with(holder.binding.ivHealthTakerRemove.getContext())
                    .load(healthTaker.getImgURL())
                    .override(200, 300)
                    .centerCrop()
                    .into(holder.binding.ivHealthTakerImg);
        }

        holder.binding.ivHealthTakerRemove.setOnClickListener(view -> {
            listener.onRemoveHealthTaker(healthTaker.getEmail());
        });
    }

    @Override
    public int getItemCount() {
        return healthTakers.size();
    }

    public void setHealthTakers(List<HealthTaker> healthTakers){
        this.healthTakers = healthTakers;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        HealthTakerItemBinding binding;
        public ViewHolder(HealthTakerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
