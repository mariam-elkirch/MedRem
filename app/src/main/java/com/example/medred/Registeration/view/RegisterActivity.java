package com.example.medred.Registeration.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medred.Home.view.HomeActivity;
import com.example.medred.R;
import com.example.medred.Registeration.presenter.RegistrationPresenter;
import com.example.medred.model.Repository;
import com.example.medred.model.User;
import com.example.medred.network.FirebaseManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements RegisterationViewInterface{

    private static final int RC_SIGN_IN =200;
    private static String TAG ="TAG" ;
    private AppCompatEditText mNameEdittext;
    private AppCompatEditText mEmailEdittext;
    private AppCompatEditText mPasswordEdit;
    private Button mSiginupButton;
    private TextView mAlreadyHaveAnAccount;
    private ImageView mGoogleIcon;
    String NameText, EmailText, PasswordText;
    ProgressDialog progressDialog;
    GoogleSignInClient mGoogleSignInClient;
    protected FirebaseAuth auth;
    RegistrationPresenter registrationPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        initView();
        registrationPresenter=new RegistrationPresenter(Repository.getInstance(this,FirebaseManager.getInstance(this)), this);
        mGoogleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignUp();
            }
        });
        mAlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        mSiginupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });
    }



    private void googleSignUp() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("975941830408-o03u9edsbg58aaqs793r0hb3dh7598k7.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signIn();
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                sendCredintalGoogle(account);
               // firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(RegisterActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        }
    }
  /*  private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = auth.getCurrentUser();

                           storeFirebaseDataGoogle(account.getDisplayName(),account.getEmail(),auth.getUid());

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "failure", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }*/


    private void inputData() {
        NameText = mNameEdittext.getText().toString().trim();
        EmailText = mEmailEdittext.getText().toString().trim();
        PasswordText = mPasswordEdit.getText().toString().trim();
        if (NameText.trim().isEmpty()) {
            mNameEdittext.setError("required");
            return;
        }

        if (EmailText.trim().isEmpty()) {
            mEmailEdittext.setError("required");
            return;

        }

        if (PasswordText.trim().isEmpty()) {
            mPasswordEdit.setError("required");
            return;
        }


        User user=new User(EmailText,NameText,PasswordText);
        sendUserEmailPass(user);
      //  CreateAccount();

    }





   /* private void storeFirebaseData() {
        progressDialog.setMessage("Saving Account Info ...");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid",""+auth.getUid());
        hashMap.put("name",""+NameText);
        hashMap.put("email",""+EmailText);
        hashMap.put("password",""+PasswordText);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user");
        databaseReference.child(auth.getUid()).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //db updated
                        progressDialog.dismiss();
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }
    private void storeFirebaseDataGoogle(String name, String email,String id) {
        progressDialog.setMessage("Saving Account Info ...");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid",""+id);
        hashMap.put("name",""+name);
        hashMap.put("email",""+email);
        hashMap.put("password",""+"Password");

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user");
        databaseReference.child(auth.getUid()).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //db updated
                        progressDialog.dismiss();
                        startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }*/

    private void initView() {
        mNameEdittext = findViewById(R.id.name_editText);
        mEmailEdittext = findViewById(R.id.email_editText);
        mPasswordEdit = findViewById(R.id.password_edit);
        mSiginupButton = findViewById(R.id.siginupButton);
        mAlreadyHaveAnAccount = findViewById(R.id.already_have_an_account);
        mGoogleIcon = findViewById(R.id.google_icon);

    }

    @Override
    public void sendUserEmailPass(User user) {
     registrationPresenter.createAccount(user);
    }


    @Override
    public void GoToHome(Boolean succes) {
        if(succes){
           // progressDialog.dismiss();
            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
            finish();
        }
       // finish();
    }

    @Override
    public void sendCredintalGoogle(GoogleSignInAccount account) {
          registrationPresenter.RegistrationGoogle(account);
    }
}