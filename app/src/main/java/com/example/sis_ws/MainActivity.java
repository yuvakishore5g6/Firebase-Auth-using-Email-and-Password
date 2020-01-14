package com.example.sis_ws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button login;
    FirebaseAuth mAuth;
    EditText editUserEmail,editPassword;
    TextView tvsignup;
    TextView tvforgotPassword;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        editUserEmail = findViewById(R.id.edt_email_login);
        editPassword = findViewById(R.id.edt_pass_login);
        progressBar = findViewById(R.id.progressBar);
        tvsignup = findViewById(R.id.tv_sign_up);
        login = findViewById(R.id.but_login);
        tvforgotPassword = findViewById(R.id.tvforgetpassword);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,Profile.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();

            }
        });
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Sign_up.class);
                startActivity(intent);

            }
        });
    tvforgotPassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            startActivity(new Intent(getApplicationContext(),forgotPasswordActivity.class));
        }
    });

    }
//    private void checkEmailVerification()
//    {
//        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
//        Boolean emailflag = firebaseUser.isEmailVerified();
//
//        if(emailflag){
//            startActivity(new Intent(MainActivity.this,Profile.class));
//
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(),"Verify your Email ",Toast.LENGTH_LONG).show();
//            mAuth.signOut();
//        }


    private void userLogin()
    {
        String email = editUserEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            editUserEmail.setError("Email is required");
            editUserEmail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editUserEmail.setError("Please enter a valid email");
            editUserEmail.requestFocus();
            return;
        }
        if(password.length() < 6)
        {
            editPassword.setError("Minimum length of password should be 6");
            editPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
            if(task.isSuccessful()){
                progressBar.setVisibility(View.GONE);
                checkEmailVerification();

//                Intent intent = new Intent(getApplicationContext(),Profile.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
//                finish();

            }
            else
            {
                Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
            }
        });

    }
    private void checkEmailVerification() {
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if (emailflag)
        {
            finish();
            startActivity(new Intent(MainActivity.this, Profile.class));
        }
        else
            {
                Toast.makeText(getApplicationContext(), "Verify your email", Toast.LENGTH_LONG).show();
                mAuth.signOut();
            }
        }

    }

