package com.example.medred.dependentsList.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medred.R;
import com.example.medred.databinding.FragmentDependantsListBinding;
import com.example.medred.db.ConcreteLocalSource;
import com.example.medred.dependentsList.presenter.DependantsListPresenter;
import com.example.medred.dependentsList.presenter.DependantsListPresenterInterface;
import com.example.medred.medicationsList.presenter.MedicationsListIPresenterInterface;
import com.example.medred.medicationsList.presenter.MedicationsListPresenter;
import com.example.medred.medicationsList.view.MedicationsListAdapter;
import com.example.medred.medicationsList.view.MedicationsListFragment;
import com.example.medred.model.Dependant;
import com.example.medred.model.Repository;
import com.example.medred.network.FirebaseManager;

import java.util.ArrayList;
import java.util.List;


public class DependantsListFragment extends Fragment implements DependantsListViewInterface{

    private FragmentDependantsListBinding binding;
    private DependantsListAdapter dependantsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DependantsListPresenterInterface dependantsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDependantsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dependantsAdapter = new DependantsListAdapter(new ArrayList<>(), this);
        binding.rvDependants.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(DependantsListFragment.this.getContext());
        binding.rvDependants.setLayoutManager(layoutManager);
        binding.rvDependants.setAdapter(dependantsAdapter);
        dependantsPresenter = new DependantsListPresenter(this,
                Repository.getInstance(this.getContext(), FirebaseManager.getInstance(this.getContext())
                        , ConcreteLocalSource.getInstance(this.getContext())));
        dependantsPresenter.getDependants();
    }

    @Override
    public void showDependants(List<Dependant> dependants) {
        if(dependants.size() == 0){
            binding.rvDependants.setVisibility(View.GONE);
        }
        else{
            binding.rvDependants.setVisibility(View.VISIBLE);
            dependantsAdapter.setDependants(dependants);
        }
    }

    @Override
    public void deleteDependant(Dependant dependant) {
        dependantsPresenter.deleteDependant(dependant);
    }

    @Override
    public void onClick(String dependantEmail) {
        dependantsPresenter.switchToDependant(dependantEmail);
    }

    @Override
    public void onDeletingDependant(boolean isDeleted) {
        Toast.makeText(this.getContext(), "Dependant is removed successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}