package com.example.medred.healthtakerslist.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.medred.R;
import com.example.medred.databinding.AddHealthTakerDialogBinding;
import com.example.medred.databinding.FragmentHealthTakersListBinding;
import com.example.medred.db.ConcreteLocalSource;
import com.example.medred.healthtakerslist.presenter.HealthTakerPresenter;
import com.example.medred.healthtakerslist.presenter.HealthTakerPresenterInterface;
import com.example.medred.model.HealthTaker;
import com.example.medred.model.Repository;
import com.example.medred.model.Request;
import com.example.medred.model.Utils;
import com.example.medred.network.FirebaseManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class HealthTakersListFragment extends Fragment implements OnHealthTakerClickListener, HealthTakersListViewInterface{

    private FragmentHealthTakersListBinding binding;
    private HealthTakersListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private HealthTakerPresenterInterface healthTakerPresenter;
    private boolean sendClicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHealthTakersListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new HealthTakersListAdapter(new ArrayList<>(), this);
        binding.rvHealthTakers.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(HealthTakersListFragment.this.getContext());
        binding.rvHealthTakers.setLayoutManager(layoutManager);
        binding.rvHealthTakers.setAdapter(adapter);
        binding.rvHealthTakers.hasFixedSize();
        healthTakerPresenter = new HealthTakerPresenter(this,
                Repository.getInstance(this.getContext(), FirebaseManager.getInstance(this.getContext())
                , ConcreteLocalSource.getInstance(this.getContext())));
        healthTakerPresenter.getHealthTakers();
        binding.btnAddHealthTaker.setOnClickListener(view1 -> onAddHealthTaker());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onRemoveHealthTaker(HealthTaker healthTaker) {
        healthTakerPresenter.deleteHealthTaker(healthTaker);
    }

    @Override
    public void onAddHealthTaker() {
        showAddHealthTakerDialog();
        //healthTakerPresenter.addHealthTaker(healthTakerEmail);
    }

    @Override
    public void showHealthTakers(List<HealthTaker> healthTakers) {
        if(healthTakers.size() == 0){
            binding.rvHealthTakers.setVisibility(View.GONE);
        }
        else{
            binding.rvHealthTakers.setVisibility(View.VISIBLE);
            adapter.setHealthTakers(healthTakers);
        }
    }

    @Override
    public void isUserExist(boolean userExistence, String receiverId) {
        if(sendClicked){
            if(userExistence == true && !receiverId.equals("none")){
                healthTakerPresenter.addHealthTaker(receiverId);
                Toast.makeText(this.getContext(), "the request is sent", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this.getContext(), "this user does not exist", Toast.LENGTH_SHORT).show();
            }
            sendClicked = false;
        }

    }

    @Override
    public void onDeletingHealthTaker(boolean isDeleted) {
        Toast.makeText(this.getContext(), "HealthTaker is removed successfully", Toast.LENGTH_SHORT).show();
    }

    public void showAddHealthTakerDialog(){

        AddHealthTakerDialogBinding binding = AddHealthTakerDialogBinding
                .inflate(LayoutInflater.from(this.getContext()));
        Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        binding.btnCancelRequest.setOnClickListener(view -> dialog.dismiss());
        binding.btnSendRequest.setOnClickListener(view -> {
            sendClicked = true;
            if(Utils.isInternetAvailable(this.getContext())){
                String email = binding.etHealthTakerEmail.getText().toString().trim();
                 if(email.isEmpty()){
                     binding.etHealthTakerEmail.setError("You must enter an email");
                 }
                 else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                     binding.etHealthTakerEmail.setError("You must enter valid email");
                 }
                 else if(email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                     binding.etHealthTakerEmail.setError("You can't send request to yourself");
                 }
                 else{
                    if(sendClicked){
                        healthTakerPresenter.userExistence(email);
                        dialog.dismiss();
                    }

                 }
            }
            else{
                Toast.makeText(this.getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}