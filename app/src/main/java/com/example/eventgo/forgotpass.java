package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventgo.databinding.ActivityForgotpassBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpass extends AppCompatActivity {
    private Button continuebutton;
    private EditText forgotmail;
    private String email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        auth = FirebaseAuth.getInstance();
        forgotmail = findViewById(R.id.forgotmail);
        continuebutton = findViewById(R.id.continuebutton);

        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }
    public void validateData()
    {
        email = forgotmail.getText().toString();
        if(email.isEmpty()){
            forgotmail.setError("Email is empty");
        }
        else
        {
            forpass();
        }
    }public void forpass()
    {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgotpass.this, "Check Your Mail", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(forgotpass.this, login_page.class));
                    finish();
                }
                else{
                    Toast.makeText(forgotpass.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}