package com.example.medred.db;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.medred.model.Medication;

import java.util.List;

public class ConcreteLocalSource implements LocalSource{
    private MedicationDAO medicationDAO;
    private static ConcreteLocalSource concreteLocalSource = null;
    private LiveData<List<Medication>> medicationModel;

    public ConcreteLocalSource(Context context) {
        AppDataBase appDataBase = AppDataBase.getInstance(context.getApplicationContext());
        medicationDAO = appDataBase.medicationDAO();
        medicationModel = medicationDAO.getAllMedications();
    }

    public static ConcreteLocalSource getInstance(Context context) {
        if (concreteLocalSource == null) {
            concreteLocalSource = new ConcreteLocalSource(context);
        }
        return concreteLocalSource;
    }


    @Override
    public void insertMedication(Medication medicationModel) {
        new Thread(() -> {
            medicationDAO.insertMedication(medicationModel);
        }).start();
    }

    @Override
    public void deleteMedication(Medication medicationModel) {
        new Thread(() -> {
            medicationDAO.deleteMedication(medicationModel);
        }).start();
        Log.d("TAG", "removeMedication: " + "medication deleted");
    }

    @Override
    public LiveData<List<Medication>> getAllMedications() {
        return medicationModel;
    }
}
