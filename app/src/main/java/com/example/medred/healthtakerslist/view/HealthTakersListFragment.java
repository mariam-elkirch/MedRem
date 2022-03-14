package com.example.medred.healthtakerslist.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.medred.databinding.FragmentHealthTakersListBinding;
import com.example.medred.healthtakerslist.presenter.HealthTakerPresenter;
import com.example.medred.healthtakerslist.presenter.HealthTakerPresenterInterface;
import com.example.medred.model.HealthTaker;
import java.util.ArrayList;
import java.util.List;


public class HealthTakersListFragment extends Fragment implements OnHealthTakerClickListener, HealthTakersListViewInterface{

    private FragmentHealthTakersListBinding binding;
    private HealthTakersListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private HealthTakerPresenterInterface healthTakerPresenter;

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
        healthTakerPresenter = new HealthTakerPresenter(this);
        healthTakerPresenter.getHealthTakers(this);
        binding.btnAddHealthTaker.setOnClickListener(view1 -> onAddHealthTaker(""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onRemoveHealthTaker(String healthTakerEmail) {
        healthTakerPresenter.deleteHealthTaker(healthTakerEmail);
    }

    @Override
    public void onAddHealthTaker(String healthTakerEmail) {
        healthTakerPresenter.addHealthTaker(healthTakerEmail);
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
}