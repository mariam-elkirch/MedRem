package com.example.medred.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.medred.model.Dependant;
import com.example.medred.model.HealthTaker;
import com.example.medred.model.Medication;
import com.example.medred.model.Request;
import com.example.medred.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseManager implements FirebaseSource {
    ProgressDialog progressDialog;
    GoogleSignInClient mGoogleSignInClient;
    protected FirebaseAuth auth;
    private static  FirebaseManager remoteSource=null;
    private NetworkDelegate networkDelegate;
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
    public void setNetworkDelegate(NetworkDelegate networkDelegate) {
        this.networkDelegate = networkDelegate;
    }

    @Override
    public void authunticateUser(User user,FireBaseCallBack fireBaseCallBack) {

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
       // return stored;
    }

    @Override
    public void RegisterationGoogle(GoogleSignInAccount account, FireBaseCallBack fireBaseCallBack) {
        auth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Toast.makeText(context, "Register Successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = auth.getCurrentUser();

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("uid", "" + auth.getUid());
                            hashMap.put("name", "" + account.getEmail());
                            hashMap.put("email", "" + account.getEmail());
                            hashMap.put("password", "" + "Password");

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
                            databaseReference.child(auth.getUid()).setValue(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //db updated
                                            // progressDialog.dismiss();
                                            success = true;
                                            fireBaseCallBack.onCallBack(success);
                                            Log.i("TAG", success + "Result");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // progressDialog.dismiss();
                                            success=false;
                                            fireBaseCallBack.onCallBack(success);
                                            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            // finish();
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        //return  stored;
    }

    @Override
    public void loginEmailPassword(String email, String password,FireBaseCallBack fireBaseCallBack) {
        auth = FirebaseAuth.getInstance();
       auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        success = true;
                        fireBaseCallBack.onCallBack(success);
                        Log.i("TAG", success + "Result");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // progressDialog.dismiss();
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void getMedications() {
        Log.i("TAG", "getmedication");
        List<Medication> medicationList = new ArrayList<>();
        Map<String, String> meds = new HashMap<>();
        DatabaseReference reference=  FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("medication");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                medicationList.clear();
                for(DataSnapshot snapshot1: datasnapshot.getChildren()){
                    Medication medicine= snapshot1.getValue(Medication.class);
                    medicationList.add(medicine);

                    Log.i("TAG",  "Result "+medicine.getName()+" "+medicationList.size());
                }
              //  success = true;
               // fireBaseCallBack.onCallBack(success,medicationList);
                networkDelegate.onSuccessMedicationList(medicationList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void forgotPassword(String email) {
        auth = FirebaseAuth.getInstance();
       auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                      //  progressDialog.dismiss();
                        Toast.makeText(context, "Password reset instructions sent to your email", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      //  progressDialog.dismiss();
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void userExistence(String email) {
        Log.i("TAG", "inside userExistence");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("user");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean exist = false;
                String receiverId = "none";
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String existedEmail = dataSnapshot.child("email").getValue().toString();
                    if(existedEmail.equals(email)){
                        receiverId = dataSnapshot.child("uid").getValue().toString();
                        exist = true;
                        break;
                    }
                }

                networkDelegate.isUserExist(exist, receiverId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void sendRequest(String receiverId) {
        Log.i("TAG", "inside sendRequest");
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user/" + uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Request request = new Request(snapshot.child("email").getValue().toString(),
                        snapshot.child("name").getValue().toString(),
                        snapshot.child("uid").getValue().toString());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                        .getReference("user/" + receiverId);
                databaseReference.child("requests").child(uid).setValue(request);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void acceptRequest(Request request) {
        Log.i("TAG", "acceptRequest");
        String uid = FirebaseAuth.getInstance().getUid();

        // add dependant to current user
        Dependant dependant = new Dependant(request.getSenderId(), request.getSenderName(),
                request.getSenderEmail(), null);
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("user/" + uid);
        reference.child("dependants").child(request.getSenderId()).setValue(dependant);

        // add healthTaker to sender
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user/" + uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HealthTaker healthTaker = new HealthTaker(
                        snapshot.child("uid").getValue().toString(),
                        snapshot.child("name").getValue().toString(),
                        snapshot.child("email").getValue().toString(),
                        null);
                DatabaseReference reference = FirebaseDatabase.getInstance()
                        .getReference("user/" + request.getSenderId());
                reference.child("healthTakers").child(uid).setValue(healthTaker);

                // remove request from current user requests list
                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                        .getReference("user/" + uid);
                databaseReference.child("requests").child(request.getSenderId()).removeValue();

                networkDelegate.onSuccessAcceptingRequest(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                networkDelegate.onSuccessAcceptingRequest(false);
            }
        });

    }

    @Override
    public void rejectRequest(Request request) {
        Log.i("TAG", "rejectRequest");
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("user/" + uid);
        databaseReference.child("requests").child(request.getSenderId()).removeValue();
        networkDelegate.onSuccessRejectingRequest(true);
    }

    @Override
    public void getRequests() {
        Log.i("TAG", "getRequests");
        List<Request> requests = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance()
                .getReference("user/"+ FirebaseAuth.getInstance().getUid()+"/requests");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requests.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("senderEmail").getValue() != null){
                        String senderEmail = dataSnapshot.child("senderEmail").getValue().toString();
                        String senderName = dataSnapshot.child("senderName").getValue().toString();
                        String senderId = dataSnapshot.child("senderId").getValue().toString();
                        Request request = new Request(senderEmail, senderName, senderId);
                        requests.add(request);
                    }
                }
                networkDelegate.onSuccessRequests(requests);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                networkDelegate.onFailureRequests(error.getMessage());
            }
        });
    }

    @Override
    public void getHealthTakers() {
        Log.i("TAG", "getHealthTakers");
        List<HealthTaker> healthTakers = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance()
                .getReference("user/"+ FirebaseAuth.getInstance().getUid()+"/healthTakers");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                healthTakers.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("email").getValue() != null){
                        String uid = dataSnapshot.child("uid").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        //String imgUrl = dataSnapshot.child("imgURL").getValue().toString();
                        HealthTaker healthTaker = new HealthTaker(uid, name, email, null);
                        healthTakers.add(healthTaker);
                    }
                }
                networkDelegate.onSuccessGettingHealthTakers(healthTakers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void getDependants() {
        Log.i("TAG", "getDependants");
        List<Dependant> dependants = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance()
                .getReference("user/"+ FirebaseAuth.getInstance().getUid()+"/dependants");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dependants.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("email").getValue() != null){
                        String uid = dataSnapshot.child("uid").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        //String imgUrl = dataSnapshot.child("imgURL").getValue().toString();
                        Dependant dependant = new Dependant(uid, name, email, null);
                        dependants.add(dependant);
                    }
                }
                networkDelegate.onSuccessGettingDependants(dependants);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void deleteHealthTaker(HealthTaker healthTaker) {
        Log.i("TAG", "deleteHealthTaker");
        String uid = FirebaseAuth.getInstance().getUid();
        // remove current user from healthTaker dependants list
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance()
                .getReference("user");

        databaseReference1.child(healthTaker.getUid() + "/dependants").child(uid).setValue(null);

        // remove healthTaker from current user healthTakers list
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance()
                .getReference("user");
        databaseReference2.child(uid + "/healthTakers").child(healthTaker.getUid()).setValue(null);
        networkDelegate.onDeletingHealthTaker(true);
    }

    @Override
    public void deleteDependant(Dependant dependant) {
        Log.i("TAG", "deleteDependant");
        String uid = FirebaseAuth.getInstance().getUid();

        // remove current user from dependant healthTakers list
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance()
                .getReference("user");
        databaseReference1.child(dependant.getUid() + "/healthTakers").child(uid).setValue(null);
        // remove dependant from current user dependants list
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance()
                .getReference("user");
        databaseReference2.child(uid + "/dependants").child(dependant.getUid()).setValue(null);
        networkDelegate.onDeletingDependant(true);
    }

    public interface FireBaseCallBack {
        void onCallBack(boolean stored);
    }
    public interface FireBaseCallBackMed {
        void onCallBack(boolean stored,   List<Medication> medicationList);
    }
}
