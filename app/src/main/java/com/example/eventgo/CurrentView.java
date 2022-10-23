package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CurrentView extends AppCompatActivity {

    TextView presentname, presentdate;
    String code;
    private Button addpeeps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_view);

        presentname = findViewById(R.id.presentname);
        presentdate = findViewById(R.id.presentdate);

        presentname.setText(getIntent().getStringExtra("Event name"));
        presentdate.setText(getIntent().getStringExtra("Event date"));


        addpeeps = findViewById(R.id.addpeople);
        addpeeps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openinvite();
            }
        });

    }
    public void openinvite(){
        Intent intent = new Intent(this, invite.class);
        code = getIntent().getStringExtra("Event key");
        intent.putExtra("Event key",code);
        startActivity(intent);
    }
}