package com.example.medred.Home.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medred.Home.presenter.HomePresenter;
import com.example.medred.Home.presenter.HomePresenterInterface;
import com.example.medred.R;
import com.example.medred.db.ConcreteLocalSource;
import com.example.medred.medicationsList.view.MedicationsListFragment;
import com.example.medred.model.Medication;
import com.example.medred.model.Reminders;
import com.example.medred.model.Repository;
import com.example.medred.network.FirebaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;



public class HomeFragment extends Fragment implements  OnHomeMedicationClickListener,HomeViewInterface{
   // private FragmentHomeBinding binding;
    private HomeMedicationAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private HomePresenterInterface presenter;
    RecyclerView recyclerView;
    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return  inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycler_view);

        adapter = new HomeMedicationAdapter(new ArrayList<>(), this);

        layoutManager = new LinearLayoutManager(HomeFragment.this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        presenter = new HomePresenter(this, Repository.getInstance(this.getContext(),
                FirebaseManager.getInstance(getActivity()), ConcreteLocalSource.getInstance(this.getContext())));

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -3);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 3);
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();
        //internet condn

       // presenter.getCalenderMedications(Calendar.getInstance().getTimeInMillis(),this);
        presenter.getFirebaseMedications(Calendar.getInstance().getTimeInMillis(),this);
        LifecycleOwner lifecycleOwner=this;
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                presenter.getFirebaseMedications(Calendar.getInstance().getTimeInMillis(),lifecycleOwner);
             //   presenter.getCalenderMedications(date.getTimeInMillis(),lifecycleOwner);
                Log.i("TAG", "CURRENT DATE IS " + date.getTimeInMillis());
            }
        });
        FloatingActionButton fabb=view.findViewById(R.id.fab);
        fabb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment MedicationsListFragment=new MedicationsListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContent, MedicationsListFragment );
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
    }

    @Override
    public void onClick(int medicationId) {
         //fing medication with this id from list

    }


    @Override
    public void getCalenderMeds(List<Medication> calenderMedications) {
        //To Do Set Alarms
       // presenter.getFirebaseMedications();
        Log.i("TAG","med"+calenderMedications.size());
       List<Reminders> reminders=presenter.getReminders(calenderMedications);
       // adapter.setMedications(calenderMedications);


        //Log.i("TAG",reminders.get(0).getName());
         adapter.setMedications(reminders);
    }





}