package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class signup_page extends AppCompatActivity {
    EditText etdate;
    DatePickerDialog.OnDateSetListener setListener;
    private TextView button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
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

}