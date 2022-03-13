package com.example.medred.model;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.medred.db.LocalSource;
import com.example.medred.network.RemoteResource;

import java.util.List;

public class MedicationRepository implements MedicationRepositoryInterface{
    private Context context;
    //RemoteResource remoteResource;
    LocalSource localSource;

    private static MedicationRepository medicationRepository = null;

    public MedicationRepository( LocalSource localSource , Context context) {
        this.context = context;
        //this.remoteResource = remoteResource;
        this.localSource = localSource;
    }


    public static MedicationRepository getInstance( LocalSource localSource , Context context){

        if(medicationRepository==null){
            medicationRepository=new MedicationRepository(localSource,context);
        }
        return medicationRepository;
    }

    @Override
    public LiveData<List<Medication>> getAllMedications() {
        return localSource.getAllMedications();
    }

    @Override
    public void getAllMedication(LocalSource localSource) {

        localSource.getAllMedications();
    }

    @Override
    public void insertMedication(Medication medicationModel) {

        localSource.insertMedication(medicationModel);
    }

    @Override
    public void deleteMedication(Medication medicationModel) {

        localSource.deleteMedication(medicationModel);
    }
}
