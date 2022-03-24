package com.example.medred.dependentsList.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.example.medred.model.Dependant;

public interface DependantsListPresenterInterface {
    void getDependants();
    void switchToDependant(String dependantEmail);
    void deleteDependant(Dependant dependant);
}
