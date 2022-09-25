package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

import java.util.Calendar;

public class signup_page extends AppCompatActivity {
    CountryCodePicker ccp;
    EditText phone;
    EditText etdate;
    Button signupbutton;
    DatePickerDialog.OnDateSetListener setListener;
    private TextView button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        phone=(EditText)findViewById(R.id.phone);
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phone);
        signupbutton=(Button)findViewById(R.id.signupbutton);

        signupbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                goToOtp();
            }

        });


        button = (TextView) findViewById(R.id.alreadyaccount);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openloginpage();
            }
        });
        etdate = findViewById(R.id.time_signup);
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
    }
    public void openloginpage(){
        Intent intent = new Intent(this, login_page.class);
        startActivity(intent);
    }
    public void goToOtp()
    {
        Intent intent=new Intent(signup_page.this,manageOTP.class);
        intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
        startActivity(intent);
    }


}