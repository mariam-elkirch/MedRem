package com.example.medred.model;

import android.content.Context;

import com.example.medred.network.FirebaseManager;
import com.example.medred.network.FirebaseSource;

public class Repository implements RepositoryInterface{
    FirebaseSource firebaseSource;
   // RemoteSource remoteSource;

    Context context;
    Boolean success=false;
    private static Repository repo = null;
    public Repository(Context context, FirebaseSource firebaseSource) {
        this.firebaseSource=firebaseSource;
        this.context=context;
    }
    public  static  Repository getInstance(Context context, FirebaseSource firebaseSource){
        if(repo==null)
            repo=new Repository(context,firebaseSource);
        return  repo;
    }
    @Override
    public Boolean authunticateUser(User user, FirebaseManager.FireBaseCallBack fireBaseCallBack) {
         // fireBaseCallBack.onCallBack(firebaseSource);
       return  firebaseSource.authunticateUser(user,fireBaseCallBack);
    }



}
