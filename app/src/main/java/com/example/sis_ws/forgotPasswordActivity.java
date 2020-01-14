package com.example.sis_ws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPasswordActivity extends AppCompatActivity {
    private EditText editText_reset_email;
    private Button button_reset;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = FirebaseAuth.getInstance();
        editText_reset_email = findViewById(R.id.et_reset_email);
        button_reset = findViewById(R.id.but_reset);

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText_reset_email.getText().toString().trim();

                if(email.equals("")) {
                    Toast.makeText(forgotPasswordActivity.this, "Please enter your registered Email Id", Toast.LENGTH_LONG).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(getApplicationContext(), "Password reset link is sent !", Toast.LENGTH_SHORT).show();
                           finish();
                           startActivity(new Intent(forgotPasswordActivity.this,MainActivity.class));
                       }
                          else
                       {
                           Toast.makeText(getApplicationContext(), "Error in sending reset link  !", Toast.LENGTH_SHORT).show();
                       }
                        }
                    });



                }
            }
        });
    }
}
