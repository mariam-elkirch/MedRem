package com.example.medred.addmedication.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.medred.addmedication.view.AddMedicationViewInterface;
import com.example.medred.model.Alarm;
import com.example.medred.model.Medication;
import com.example.medred.model.RepositoryInterface;

import java.util.List;

public class MedicationPresenter implements AddMedicationPresenterInterface {

    AddMedicationViewInterface addMedicationView;
    RepositoryInterface repository;
    int id;


    public MedicationPresenter(AddMedicationViewInterface addMedicationView, RepositoryInterface repository) {
      this.addMedicationView=addMedicationView;
      this.repository=repository;
    }



    @Override
    public void setMedicationPresenter(Medication medicationModel) {
       repository.insertMedication(medicationModel);
    }

    @Override
    public LiveData<Medication> getShowMed(int idMed) {
        return repository.getShowMedication(idMed);
    }

    @Override
    public void updateMedication(Medication medicationModel) {
        repository.updateMedication(medicationModel);
    }

    @Override
    public void takeMedication(String pillStock, int Id) {
        repository.takeMedication(pillStock, Id);
    }

    @Override
    public void rescheduleMedication(Alarm alarm, int Id) {

        repository.rescheduleMedication(alarm, Id);
    }


    @Override
    public void getIdFromShow(int id) {
        this.id=id;
    }


}
