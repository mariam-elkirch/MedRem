package com.example.medred.addmedication.presenter;

import com.example.medred.addmedication.view.AddMedicationViewInterface;
import com.example.medred.model.Medication;
import com.example.medred.model.MedicationRepository;
import com.example.medred.model.MedicationRepositoryInterface;

public class MedicationPresenter implements AddMedicationPresenterInterface {

    AddMedicationViewInterface addMedicationViewInterface;
    MedicationRepositoryInterface medicationRepositoryInterface;

    public MedicationPresenter(AddMedicationViewInterface addMedicationViewInterface, MedicationRepositoryInterface medicationRepositoryInterface) {
        this.addMedicationViewInterface = addMedicationViewInterface;
        this.medicationRepositoryInterface = medicationRepositoryInterface;
    }

    @Override
    public void setMedicationPresenter(Medication medicationModel) {
        medicationRepositoryInterface.insertMedication(medicationModel);
    }
}
