package com.example.medred.addmedication.presenter;

import com.example.medred.addmedication.view.AddMedicationViewInterface;
import com.example.medred.model.Medication;
import com.example.medred.model.RepositoryInterface;

public class MedicationPresenter implements AddMedicationPresenterInterface {

    AddMedicationViewInterface addMedicationViewInterface;
   RepositoryInterface repositoryInterface;

    public MedicationPresenter(AddMedicationViewInterface addMedicationViewInterface, RepositoryInterface repositoryInterface) {
        this.addMedicationViewInterface = addMedicationViewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public void setMedicationPresenter(Medication medicationModel) {
        repositoryInterface.insertMedication(medicationModel);
    }
}
