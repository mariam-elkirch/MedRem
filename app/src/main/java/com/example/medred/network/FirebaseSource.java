package com.example.medred.network;

import com.example.medred.model.User;

public interface FirebaseSource {
Boolean authunticateUser(User user, FirebaseManager.FireBaseCallBack fireBaseCallBack);

}
