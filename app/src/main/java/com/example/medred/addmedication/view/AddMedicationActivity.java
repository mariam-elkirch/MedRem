package com.example.medred.addmedication.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.medred.R;
import com.example.medred.model.Medication;


public class AddMedicationActivity extends AppCompatActivity {

    public static Medication medicationMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        medicationMain = new Medication();
    }
}