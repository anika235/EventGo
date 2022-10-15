package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CurrentView extends AppCompatActivity {

    TextView presentname, presentdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_view);

        presentname = findViewById(R.id.presentname);
        presentdate = findViewById(R.id.presentdate);

        presentname.setText(getIntent().getStringExtra("Event name"));
        presentdate.setText(getIntent().getStringExtra("Event date"));

    }
}