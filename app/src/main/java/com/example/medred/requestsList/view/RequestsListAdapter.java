package com.example.medred.requestsList.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.medred.databinding.RequestItemBinding;
import com.example.medred.model.Request;
import java.util.List;


public class RequestsListAdapter extends RecyclerView.Adapter<RequestsListAdapter.ViewHolder>{

    private List<Request> requests;
    private OnRequestClickListener listener;
    public RequestsListAdapter(List<Request> requests, OnRequestClickListener listener){
        this.requests = requests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(RequestItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request request = requests.get(position);
        holder.binding.tvSenderEmail.setText(request.getSenderEmail());
        holder.binding.tvSenderNameAndMessage
                .setText(request.getSenderName()
                + " wants you to be his health taker, Do you want to accept him as one of your dependants?");
        holder.binding.btnDependantAccept.setOnClickListener(view -> {
            listener.onAcceptRequest(request.getSenderEmail());
        });

        holder.binding.btnDependantRefuse.setOnClickListener(view -> {
            listener.onRefuseRequest(request.getSenderEmail());
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public void setRequests(List<Request> requests){
        this.requests = requests;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RequestItemBinding binding;
        public ViewHolder(RequestItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
