package com.example.medred.Registeration.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medred.R;
import com.example.medred.Registeration.presenter.RegistrationPresenter;
import com.example.medred.model.Repository;
import com.example.medred.network.FirebaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordViewInterface{
    protected EditText email;
    protected Button recover;
    RegistrationPresenter registrationPresenter;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        recover=findViewById(R.id.recoverButton);
        email=findViewById(R.id.email_editText);
        registrationPresenter=new RegistrationPresenter(Repository.getInstance(this, FirebaseManager.getInstance(this),null), this);
        firebaseAuth = FirebaseAuth.getInstance();
      //  progressDialog = new ProgressDialog(this);
       // progressDialog.setTitle("Please wait");
       // progressDialog.setCanceledOnTouchOutside(false);



        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recoverPassword();
            }
        });
    }

    private String Email;
    private void recoverPassword() {
        Email = email.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }
       // progressDialog.setMessage("Sending instructions to reset passwords");
       // progressDialog.show();
          forgotPassword(Email);

    }

    @Override
    public void forgotPassword(String email) {
            registrationPresenter.forgotPassword(email);

    }
}