package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class third_page extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText etdate;
    EditText ettime;
    EditText title, times, dates;
    private Button button;
    Spinner type;
    int ethour, etmin;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_page);
        etdate = findViewById(R.id.dates);
        ettime = findViewById(R.id.times);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {etdate.setShowSoftInputOnFocus(false);
         ettime.setShowSoftInputOnFocus(false);}

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        third_page.this, new DatePickerDialog.OnDateSetListener() {
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

        Spinner spinner = findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Type, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("Event name");

        ettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        third_page.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                ethour = hour;
                                etmin = minute;

                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(0, 0, 0, ethour, etmin);
                                ettime.setText(DateFormat.format("hh:mm aa", calendar1));

                            }
                        },12,0, false
                );
                timePickerDialog.updateTime(ethour, etmin);
                timePickerDialog.show();
            }
        });

        title = findViewById(R.id.title);
        times = findViewById(R.id.times);
        dates = findViewById(R.id.dates);
        type = (Spinner)findViewById(R.id.type);

        button = findViewById(R.id.create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openinfopage();
            }
        });

    }
    public void openinfopage()
    {
        Intent intent = new Intent(this, infosActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) {
            Toast.makeText(getApplicationContext(),"please select a type",Toast.LENGTH_SHORT).show();
            return;
        }
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void nextpage()
     {
     String tit = title.getText().toString();
     String tim = times.getText().toString();
     String dat = dates.getText().toString();
     //String typ = type.getText().toString();

     if(tit.isEmpty())
     {
     title.setError("Title is required");
     title.requestFocus();
     }
     else if(tim.isEmpty())
     {
     times.setError("Time is required");
     times.requestFocus();
     }
     else if(dat.isEmpty())
     {
     dates.setError("Date is required");
     dates.requestFocus();
     }
//     else if(typ.isEmpty())
//     {
//     type.setError("Type is required");
//     type.requestFocus();
//     }
     else
     {

     }

     }
}
