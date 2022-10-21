package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;

public class signup_page extends AppCompatActivity {
    CountryCodePicker ccp;
    EditText firstname,lastname,password,confirmpassword,phone,email;
    EditText etdate;
    Button signupbutton;
    DatePickerDialog.OnDateSetListener setListener;
    private TextView button;

    ImageView imageViewpass;
    ImageView imageViewconf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        firstname=(EditText)findViewById(R.id.firstname);
        lastname=(EditText)findViewById(R.id.lastname);
        email=(EditText)findViewById(R.id.editTextTextEmailAddress);
        phone=(EditText)findViewById(R.id.phone);
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phone);
        etdate = findViewById(R.id.time_signup);
        password=(EditText)findViewById(R.id.password);
        confirmpassword=(EditText)findViewById(R.id.confirm_password);
        signupbutton=(Button)findViewById(R.id.signupbutton);
        button = (TextView) findViewById(R.id.alreadyaccount);


        signupbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                sign_up();
            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openloginpage();
            }
        });

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {etdate.setShowSoftInputOnFocus(false);}

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        signup_page.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date= day+"/"+month+"/"+year;
                        etdate.setText(date);

                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        imageViewpass = findViewById(R.id.passeye);
        imageViewpass.setImageResource(R.drawable.hide_password);
        imageViewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewpass.setImageResource(R.drawable.hide_password);
                }
                else
                {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewpass.setImageResource(R.drawable.show_pass);
                }

            }
        });

        imageViewconf = findViewById(R.id.confirmeye);
        imageViewconf.setImageResource(R.drawable.hide_password);
        imageViewconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirmpassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    confirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewconf.setImageResource(R.drawable.hide_password);
                }
                else
                {
                    confirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewconf.setImageResource(R.drawable.show_pass);

                }

            }
        });
    }
    public void openloginpage(){
        Intent intent = new Intent(this, login_page.class);
        startActivity(intent);
    }

    public void sign_up()
    {
        String fname=firstname.getText().toString();
        String lname=lastname.getText().toString();
        String mail=email.getText().toString();
        String pnumber=phone.getText().toString();
        String bdate=etdate.getText().toString();
        String pass=password.getText().toString();
        String c_pass=confirmpassword.getText().toString();

        if(fname.isEmpty())
        {
            firstname.setError("First Name is required");
            firstname.requestFocus();
        }
        else if(lname.isEmpty())
        {
            lastname.setError("Last Name is required");
            lastname.requestFocus();
        }
        else if(mail.isEmpty())
        {
            email.setError("Email is required");
            email.requestFocus();
        }
        else if(pnumber.isEmpty())
        {
            phone.setError("Phone Number is required");
            phone.requestFocus();
        }
        else if(bdate.isEmpty())
        {
            etdate.setError("Birthdate is required");
            etdate.requestFocus();
        }
        else if(pass.isEmpty())
        {
            password.setError("Password is required");
            password.requestFocus();
        }
        else if(pass.length()<6)
        {
            password.setError("Password must be six characters long");
            password.requestFocus();
        }
        else if(c_pass.isEmpty())
        {
            confirmpassword.setError("You need to confirm your password");
            confirmpassword.requestFocus();
        }
        else if(!pass.equals(c_pass))
        {
            confirmpassword.setError("Password does not match");
            confirmpassword.requestFocus();
        }
        else

        {
            //signupwithemail(fname,lname,mail,pnumber,bdate,pass);
            goToOtp(fname,lname,mail,bdate,pass);

        }

    }

   /* public void signupwithemail(String fname,String lname,String mail,String pnumber,String bdate,String pass)
    {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User user =new User(fname,lname,pnumber,bdate);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(signup_page.this,"Registration Successful",Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Toast.makeText(signup_page.this,"Registration Failed",Toast.LENGTH_LONG).show();

                            }

                        }
                    });
                }
                else
                {
                    Toast.makeText(signup_page.this,"Verification Problem",Toast.LENGTH_LONG).show();

                }
            }
        });
    }*/


    public void goToOtp(String fname,String lname,String mail,String bdate,String pass)
    {
        Intent intent=new Intent(signup_page.this,manageOTP.class);
/*        bundle.putString("firstname",fname);
        bundle.putString("lastname",lname);
        bundle.putString("birthdate",bdate);
        bundle.putString("password",pass);
        intent.putExtras(bundle);*/
        intent.putExtra("FirstName",fname);
        intent.putExtra("Lastname",lname);
        intent.putExtra("Birthdate",bdate);

        intent.putExtra("Email", mail);
        intent.putExtra("Password",pass);

        intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
        startActivity(intent);
    }


}