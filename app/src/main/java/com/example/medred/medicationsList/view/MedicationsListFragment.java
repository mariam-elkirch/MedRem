package com.example.medred.medicationsList.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medred.addmedication.view.AddMedicationActivity;
import com.example.medred.databinding.FragmentMedicationsListBinding;
import com.example.medred.db.ConcreteLocalSource;
import com.example.medred.medicationsList.presenter.MedicationsListIPresenterInterface;
import com.example.medred.medicationsList.presenter.MedicationsListPresenter;
import com.example.medred.model.Medication;
import com.example.medred.model.Repository;

import java.util.ArrayList;
import java.util.List;


public class MedicationsListFragment extends Fragment implements OnMedicationClickListener, MedicationsListViewInterface{

    private FragmentMedicationsListBinding binding;
    private MedicationsListAdapter activeListAdapter,inactiveListAdapter;
    private RecyclerView.LayoutManager activeLayoutManager;
    private RecyclerView.LayoutManager inActiveLayoutManager;
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

        activeLayoutManager = new LinearLayoutManager(MedicationsListFragment.this.getContext());
        inActiveLayoutManager = new LinearLayoutManager(MedicationsListFragment.this.getContext());

        binding.rvActiveMeds.setLayoutManager(activeLayoutManager);
        binding.rvInactiveMeds.setLayoutManager(inActiveLayoutManager);

        binding.rvActiveMeds.setAdapter(activeListAdapter);
        binding.rvInactiveMeds.setAdapter(inactiveListAdapter);

        medsPresenter = new MedicationsListPresenter(this, Repository.getInstance(this.getContext(),
                null, ConcreteLocalSource.getInstance(this.getContext())));
        medsPresenter.getActiveMedications(this);
        medsPresenter.getInactiveMedications(this);
        binding.btnAddMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddMedicationActivity.class);
                startActivity(intent);
            }
        });
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
    public void onDelete(Medication medication) {
        medsPresenter.deleteMedication(medication);
        Toast.makeText(this.getContext(), "medication: " + medication.getName() + " is deleted", Toast.LENGTH_SHORT).show();
    }
}