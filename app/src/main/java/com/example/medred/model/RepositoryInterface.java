package com.example.medred.model;

import com.example.medred.network.FirebaseManager;

public interface RepositoryInterface {
Boolean authunticateUser(User user, FirebaseManager.FireBaseCallBack fireBaseCallBack);

}
