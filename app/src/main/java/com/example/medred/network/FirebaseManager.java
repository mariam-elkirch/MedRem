package com.example.medred.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.medred.Home.view.HomeActivity;
import com.example.medred.Registeration.view.RegisterActivity;
import com.example.medred.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FirebaseManager implements  FirebaseSource {
    ProgressDialog progressDialog;
    GoogleSignInClient mGoogleSignInClient;
    protected FirebaseAuth auth;
    private static  FirebaseManager remoteSource=null;
    Context context;
    Boolean stored=false;
    Boolean success=false;
    private  static FirebaseSource instance=null;
    private FirebaseManager(Context context){
      this.context=context;
    }
    public  static FirebaseManager getInstance(Context context){
        if(remoteSource==null)
           remoteSource=new FirebaseManager(context);
        return remoteSource;
    }

    @Override
    public Boolean authunticateUser(User user,FireBaseCallBack fireBaseCallBack) {
     //   progressDialog.setMessage("Creating Account...");
      //  progressDialog.show();
        auth = FirebaseAuth.getInstance();
       // progressDialog = new ProgressDialog(context);

        //create account
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                    @Override
                    public void onSuccess(AuthResult authResult) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("uid",""+auth.getUid());
                        hashMap.put("name",""+user.getName());
                        hashMap.put("email",""+user.getEmail());
                        hashMap.put("password",""+user.getPassword());
                        Log.i("TAG",user.getEmail()+"Result");
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user");
                        databaseReference.child(auth.getUid()).setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        stored=true;
                                        fireBaseCallBack.onCallBack(stored);
                                        Log.i("TAG",success+"Result");
                       /* progressDialog.dismiss();
                        startActivity(new Intent(context, HomeActivity.class));
                        finish();*/
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // progressDialog.dismiss();
                                        //  Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // progressDialog.dismiss();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                        stored=false;
                        fireBaseCallBack.onCallBack(stored);
                    }
                });
        return stored;
    }

    public interface FireBaseCallBack {
        void onCallBack(boolean stored);
    }
}
