package com.example.medred.dependentsList.view;

import com.example.medred.model.Dependant;

import java.util.List;

public interface DependantsListViewInterface {
    void showDependants(List<Dependant> dependants);
    void deleteDependant(String dependantEmail);
    void onClick(String dependantEmail);
}
