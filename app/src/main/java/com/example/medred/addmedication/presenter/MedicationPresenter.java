package com.example.medred.addmedication.presenter;

import com.example.medred.addmedication.view.AddMedicationViewInterface;
import com.example.medred.model.Medication;
import com.example.medred.model.RepositoryInterface;

public class MedicationPresenter implements AddMedicationPresenterInterface {

    AddMedicationViewInterface addMedicationView;
    RepositoryInterface repository;

    public MedicationPresenter(AddMedicationViewInterface addMedicationView, RepositoryInterface repository) {
      this.addMedicationView=addMedicationView;
      this.repository=repository;
    }



    @Override
    public void setMedicationPresenter(Medication medicationModel) {
       repository.insertMedication(medicationModel);
    }
}
