package com.example.medred.dependentsList.presenter;

import androidx.lifecycle.LifecycleOwner;
import com.example.medred.dependentsList.view.DependantsListViewInterface;

public class DependantsListPresenter implements DependantsListPresenterInterface{

    DependantsListViewInterface dependantsView;
    public DependantsListPresenter(DependantsListViewInterface dependantsView){
        this.dependantsView = dependantsView;
    }

    @Override
    public void getDependants(LifecycleOwner owner) {

    }

    @Override
    public void switchToDependant(String dependantEmail) {

    }

    @Override
    public void deleteDependant(String dependantEmail) {

    }
}
