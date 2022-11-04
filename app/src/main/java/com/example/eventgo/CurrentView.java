package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CurrentView extends AppCompatActivity {

    TextView presentname, presentdate;
    ImageView event_header,budget;
    ImageView place;
    String code;
    private Button addpeeps;
    private ImageView checklist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_view);

        presentname = findViewById(R.id.presentname);
        presentdate = findViewById(R.id.presentdate);
        event_header=findViewById(R.id.header_image);
        place=findViewById(R.id.place);
        budget=findViewById(R.id.money);

        presentname.setText(getIntent().getStringExtra("Event name"));
        presentdate.setText(getIntent().getStringExtra("Event date"));
        Glide.with(this).load(getIntent().getStringExtra("Event Image")).into(event_header);


        addpeeps = findViewById(R.id.addpeople);
        addpeeps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openinvite();
            }
        });

        checklist = findViewById(R.id.checklistphoto);
        checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChecklist();
            }
        });
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key=getIntent().getStringExtra("Event key");
                Intent intent=new Intent(getApplicationContext(),CurrentVenue.class);
                intent.putExtra("Event Key",key);
                startActivity(intent);


            }
        });
        budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key=getIntent().getStringExtra("Event key");
                Intent intent=new Intent(getApplicationContext(),CurrentBudget.class);
                intent.putExtra("Event Key",key);
                startActivity(intent);


            }
        });

    }
    public void openinvite(){
        Intent intent = new Intent(this, invite.class);
        code = getIntent().getStringExtra("Event key");
        intent.putExtra("Event key",code);
        startActivity(intent);
    }
    public void openChecklist()
    {
        Intent intent1 = new Intent(this, checklistActivity.class);
        code = getIntent().getStringExtra("Event key");
        intent1.putExtra("Event key",code);
        startActivity(intent1);

    }
}