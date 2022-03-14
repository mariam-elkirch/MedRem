package com.example.medred.medicationsList.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medred.databinding.FragmentMedicationsListBinding;
import com.example.medred.medicationsList.presenter.MedicationsListIPresenterInterface;
import com.example.medred.medicationsList.presenter.MedicationsListPresenter;
import com.example.medred.model.Medication;

import java.util.ArrayList;
import java.util.List;


public class MedicationsListFragment extends Fragment implements OnMedicationClickListener, MedicationsListViewInterface{

    private FragmentMedicationsListBinding binding;
    private MedicationsListAdapter activeListAdapter,inactiveListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MedicationsListIPresenterInterface medsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedicationsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activeListAdapter = new MedicationsListAdapter(new ArrayList<>(), this);
        inactiveListAdapter = new MedicationsListAdapter(new ArrayList<>(), this);

        binding.tvActiveMeds.setVisibility(View.GONE);
        binding.rvActiveMeds.setVisibility(View.GONE);

        binding.tvInactiveMeds.setVisibility(View.GONE);
        binding.rvInactiveMeds.setVisibility(View.GONE);

        layoutManager = new LinearLayoutManager(MedicationsListFragment.this.getContext());

        binding.rvActiveMeds.setLayoutManager(layoutManager);
        binding.rvInactiveMeds.setLayoutManager(layoutManager);

        binding.rvActiveMeds.setAdapter(activeListAdapter);
        binding.rvInactiveMeds.setAdapter(inactiveListAdapter);

        medsPresenter = new MedicationsListPresenter(this);
        medsPresenter.getActiveMedications(this);
        medsPresenter.getInactiveMedications(this);

        //TODO: complete the presenter & dependant functionalities
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void getActiveMeds(List<Medication> activeMedications) {
        if(activeMedications.size() == 0){
            binding.tvActiveMeds.setVisibility(View.GONE);
            binding.rvActiveMeds.setVisibility(View.GONE);
        }
        else{
            binding.tvActiveMeds.setVisibility(View.VISIBLE);
            binding.rvActiveMeds.setVisibility(View.VISIBLE);
            activeListAdapter.setMedications(activeMedications);
        }
    }

    @Override
    public void getInactiveMeds(List<Medication> inactiveMedications) {
        if(inactiveMedications.size() == 0){
            binding.tvInactiveMeds.setVisibility(View.GONE);
            binding.rvInactiveMeds.setVisibility(View.GONE);
        }
        else{
            binding.tvInactiveMeds.setVisibility(View.VISIBLE);
            binding.rvInactiveMeds.setVisibility(View.VISIBLE);
            inactiveListAdapter.setMedications(inactiveMedications);
        }
    }

    @Override
    public void onClick(int medicationId) {

    }

    @Override
    public void onEdit(int medicationId) {

    }

    @Override
    public void onDelete(int medicationId) {

    }
}