package com.example.medred.dependentsList.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.medred.R;
import com.example.medred.databinding.DependantItemBinding;
import com.example.medred.model.Dependant;
import java.util.List;


public class DependantsListAdapter extends RecyclerView.Adapter<DependantsListAdapter.ViewHolder> {

    private List<Dependant> dependants;
    private DependantsListViewInterface listener;
    public DependantsListAdapter(List<Dependant> dependants, DependantsListViewInterface listener){
        this.dependants = dependants;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { ;
        return new ViewHolder(DependantItemBinding.inflate(LayoutInflater.from(parent.getContext())
        , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dependant dependant = dependants.get(position);
        holder.binding.tvDependantName.setText(dependant.getName());
        holder.binding.tvDependantEmail.setText(dependant.getEmail());

        if(dependant.getImgURL() == null){
            holder.binding.ivDependentImg.setImageResource(R.drawable.ic_userr);
        }else{
            Glide.with(holder.binding.ivDependentImg.getContext())
                    .load(dependant.getImgURL())
                    .override(200, 300)
                    .centerCrop()
                    .into(holder.binding.ivDependentImg);
        }

        holder.binding.btnDependantRemove.setOnClickListener(view -> {
            listener.deleteDependant(dependant);
        });

        holder.binding.cvDependantItem.setOnClickListener(view -> listener.onClick(dependant.getUid()));
    }

    @Override
    public int getItemCount() {
        return dependants.size();
    }

    public void setDependants(List<Dependant> dependants){
        this.dependants = dependants;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private DependantItemBinding binding;
        public ViewHolder(DependantItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
