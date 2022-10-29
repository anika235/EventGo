package com.example.eventgo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.Callback;

public class manageOTP extends AppCompatActivity {
    EditText otp;
    Button verify;
    String phonenumber;
    String otpid;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);


        phonenumber=getIntent().getStringExtra("mobile");
        otp=(EditText) findViewById(R.id.otp);
        verify=(Button) findViewById(R.id.verify);

        initiateotp();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Blank Field can not be processed",Toast.LENGTH_LONG).show();

                }
                else if(otp.getText().toString().length()!=6)
                {
                    Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();

                }
                else
                {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });


    }
    public void initiateotp()
    {

// Whenever verification is triggered with the whitelisted number,
// provided it is not set for auto-retrieval, onCodeSent will be triggered.
        auth = FirebaseAuth.getInstance();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phonenumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpid=verificationId;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        // Sign in with the credential
                        // ...
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        // ...
                        Toast.makeText(getApplicationContext(),"Verification Failed",Toast.LENGTH_LONG).show();

                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String mail=getIntent().getStringExtra("Email");
                            String pass=getIntent().getStringExtra("Password");

                            auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        String firstname=getIntent().getStringExtra("FirstName");
                                        String lastname=getIntent().getStringExtra("Lastname");
                                        String number=getIntent().getStringExtra("mobile");
                                        String birthdate=getIntent().getStringExtra("Birthdate");
                                        User user=new User(firstname,lastname,mail,number,birthdate);
                                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(manageOTP.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(manageOTP.this,login_page.class));
                                                }
                                                else
                                                {
                                                    Toast.makeText(manageOTP.this,"Registration failed!",Toast.LENGTH_LONG).show();


                                                }

                                            }
                                        });

                                    }
                                    else
                                    {
                                        Toast.makeText(manageOTP.this,"Email already Taken!",Toast.LENGTH_LONG).show();

                                    }

                                }
                            });


                        }
                        else
                        {
                            Toast.makeText(manageOTP.this,"OTP verification failed!",Toast.LENGTH_LONG).show();

                        }
                    }



                });
    }

    }