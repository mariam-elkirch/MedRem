package com.example.medred.dependentsList.view;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medred.Home.view.HomeActivity;
import com.example.medred.Home.view.HomeFragment;
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
import com.example.medred.model.Utils;
import com.example.medred.network.FirebaseManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class DependantsListFragment extends Fragment implements DependantsListViewInterface{

    private FragmentDependantsListBinding binding;
    private DependantsListAdapter dependantsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DependantsListPresenterInterface dependantsPresenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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

        initSharedPrefs();

        dependantsAdapter = new DependantsListAdapter(new ArrayList<>(), this);
        binding.rvDependants.setVisibility(View.GONE);
        binding.btnBackToMyProfile.setVisibility(View.GONE);
        binding.tvDataUnavailable.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(DependantsListFragment.this.getContext());
        binding.rvDependants.setLayoutManager(layoutManager);
        binding.rvDependants.setAdapter(dependantsAdapter);

        if(!sharedPreferences.getBoolean(Utils.IS_DEPENDANT_KEY, false)){

            dependantsPresenter = new DependantsListPresenter(this,
                    Repository.getInstance(this.getContext(), FirebaseManager.getInstance(this.getContext())
                            , ConcreteLocalSource.getInstance(this.getContext())));
            dependantsPresenter.getDependants();
        }
        else{
            binding.btnBackToMyProfile.setVisibility(View.VISIBLE);
            binding.tvDataUnavailable.setVisibility(View.VISIBLE);
            binding.btnBackToMyProfile.setOnClickListener(view1 -> {
                editor.putString(Utils.UID_KEY, FirebaseAuth.getInstance().getCurrentUser().getUid());
                editor.putBoolean(Utils.IS_DEPENDANT_KEY, false);
                editor.commit();
                backToHome();
            });
        }
    }

    private void initSharedPrefs() {
        sharedPreferences = getActivity().getSharedPreferences(Utils.SHARED_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
    public void onClick(String dependantUid) {
        if(dependantUid != null){
            editor.putString(Utils.UID_KEY, dependantUid);
            editor.putBoolean(Utils.IS_DEPENDANT_KEY, true);
            editor.commit();
            backToHome();
        }
        //dependantsPresenter.switchToDependant(dependantUid);
    }

    public void backToHome(){
//        Fragment HomeFragment = new HomeFragment();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.flContent, HomeFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();

        Intent i = new Intent(getActivity(), HomeActivity.class);
        startActivity(i);
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