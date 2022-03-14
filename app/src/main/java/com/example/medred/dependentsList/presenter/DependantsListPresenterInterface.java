package com.example.medred.dependentsList.presenter;

import androidx.lifecycle.LifecycleOwner;

public interface DependantsListPresenterInterface {
    void getDependants(LifecycleOwner owner);
    void switchToDependant(String dependantEmail);
    void deleteDependant(String dependantEmail);
}
