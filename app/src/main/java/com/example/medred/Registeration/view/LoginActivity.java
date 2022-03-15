package com.example.medred.Registeration.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity implements  LoginViewInterface{
    private static final int RC_SIGN_IN =200;
    private AppCompatEditText mEmailEdittext;
    private AppCompatEditText mPasswordEdit;
    private TextView mForgetPassText;
    private Button mLoginButton;
    private TextView mDontHaveAccount;
    private ImageView mGoogleIcon;
    private ImageView mFacebookIcon;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String Email, Password;
    GoogleSignInClient mGoogleSignInClient;
    RegistrationPresenter registrationPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        registrationPresenter=new RegistrationPresenter(Repository.getInstance(this, FirebaseManager.getInstance(this),null), this);
        mForgetPassText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        mDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        mGoogleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginGoogle();
            }
        });
    }

    private void loginGoogle() {
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
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(LoginActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
          FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
    }

    private void loginUser() {
        Email = mEmailEdittext.getText().toString().trim();
        Password = mPasswordEdit.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            Toast.makeText(this,"Invalid email pattern", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)){
            Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show();
            return;
        }
          loginEmailPassword(Email,Password);
      //  progressDialog.setMessage("Logging in");
       // progressDialog.show();


    }
    private void initView() {
        mEmailEdittext = findViewById(R.id.email_editText);
        mPasswordEdit = findViewById(R.id.password_edit);
        mForgetPassText = findViewById(R.id.forget_pass_text);
        mLoginButton = findViewById(R.id.loginButton);
        mDontHaveAccount = findViewById(R.id.dont_have_account);
       mGoogleIcon = findViewById(R.id.google_icon);

    }

    @Override
    public void loginEmailPassword(String email, String password) {
        registrationPresenter.loginEmailPassword(email,password);
    }

    @Override
    public void GoToHome(Boolean succes) {
       // progressDialog.dismiss();
        if(succes){
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();}

    }

    @Override
    public void sendCredintalGoogle(GoogleSignInAccount account) {
             registrationPresenter.LoginGoogle(account);
    }
}