package com.example.medred.addmedication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.example.medred.R;

public class ShowMedicationActivity extends AppCompatActivity {

    static int idOfMedication ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medication);
        //receive
        idOfMedication = getIntent().getExtras().getInt("IDFromOrigin");
        Bundle bundle = new Bundle();//create bundle instance
        bundle.putInt("ID",idOfMedication);
        ShowMedication showMedication = new ShowMedication();
        showMedication.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView2,showMedication).commit();




    }





}