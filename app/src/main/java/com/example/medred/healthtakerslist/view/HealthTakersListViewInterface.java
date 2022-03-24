package com.example.medred.healthtakerslist.view;

import com.example.medred.model.HealthTaker;

import java.util.List;

public interface HealthTakersListViewInterface {
    void showHealthTakers(List<HealthTaker> healthTakers);
    void isUserExist(boolean userExistence, String receiverId);
    void onDeletingHealthTaker(boolean isDeleted);
}
