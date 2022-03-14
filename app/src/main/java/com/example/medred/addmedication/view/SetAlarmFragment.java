package com.example.medred.addmedication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.medred.R;
import com.example.medred.model.Alarm;

import java.util.ArrayList;


public class SetAlarmFragment extends Fragment {

    View view;
    AlarmAdapter alarmAdapter;
    RecyclerView recyclerViewAlarm;
    int numberDoses;
    Button nextBtnAlarm;

    public SetAlarmFragment(int numberDoses){

        this.numberDoses=numberDoses;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_set_alarm, container, false);

        //createAlarm();
        recyclerViewAlarm = view.findViewById(R.id.recyclerViewAlarm);
        alarmAdapter = new AlarmAdapter();
        alarmAdapter.setData(getData(numberDoses));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAlarm.setLayoutManager(linearLayoutManager);
        recyclerViewAlarm.setAdapter(alarmAdapter);

        nextBtnAlarm=view.findViewById(R.id.nextBtnAlarm);
        nextBtnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(alarmAdapter.alarmCheck()==1){
                   replaceFragment(new AddMedicationFinal());
                   //Navigation.findNavController(view).navigate(R.id.action_setAlarmFragment_to_addMedicationFinal);
               } else{
                   Toast.makeText(getContext(), "please fill all fields", Toast.LENGTH_SHORT).show();
               }
            }
        });

        return view;
    }

    public ArrayList<Alarm> getData(int numberDoses){
        ArrayList<Alarm> alArrayList = new ArrayList<>();
        for(int i = 0 ; i<numberDoses ; i++){
            alArrayList.add(new Alarm(0,0,"AM"));
        }
        return  alArrayList;
    }


    public void replaceFragment(Fragment someFragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}