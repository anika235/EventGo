package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Year;
import java.util.Calendar;

public class third_page extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button button;
    private EditText etdate;
    private EditText ettime;
    private EditText title, times, dates;
    public String typ;
    Spinner type;
    private int notificationId=1;
    int ethour, etmin;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_page);
        etdate = findViewById(R.id.dates);
        ettime = findViewById(R.id.times);

        button=(Button) findViewById(R.id.create);

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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextpage();
            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) {
            Toast.makeText(getApplicationContext(),"please select a type",Toast.LENGTH_SHORT).show();
            return;
        }
        String text = parent.getItemAtPosition(position).toString();
        typ=text;
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
         String[] values = dat.split("/");
         int day = Integer.parseInt(values[0]);
         int month = Integer.parseInt(values[1]);
         int year = Integer.parseInt(values[2]);

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
     else if(typ.isEmpty())
     {
        Toast.makeText(this,"Event Type is Required",Toast.LENGTH_LONG).show();
        type.requestFocus();
    }
     else
     {
         Event event=new Event(tit,typ,dat,tim);
         String key=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").push().getKey();

         FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(key).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful())
                 {


                     Toast.makeText(getApplicationContext(),"Event added Successfully",Toast.LENGTH_LONG).show();
                     Intent intent=new Intent(getApplicationContext(),infosActivity.class);
                     intent.putExtra("Key",key);
                     startActivity(intent);
                     finish();

                 }
                 else
                 {
                     Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();

                 }

             }
         });

     }

     }




}
