package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class VenueDetails extends AppCompatActivity {
    TextView venueTitle,venueLocation;
    EditText venueGuest,venueRent,eventType;
    ImageView venueImage;
    Button addVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);
        venueTitle=(TextView) findViewById(R.id.venueTitle);
        venueLocation=(TextView) findViewById(R.id.venueLocation);
        venueImage=(ImageView)findViewById(R.id.venuePhoto);
        venueGuest=(EditText) findViewById(R.id.capacity);
        venueRent=(EditText) findViewById(R.id.rent);
        eventType=(EditText) findViewById(R.id.venueEvent);
        addVenue=(Button)findViewById(R.id.addVenue);

        venueTitle.setText(getIntent().getStringExtra("VenueName"));
        venueLocation.setText(getIntent().getStringExtra("VenueLocation"));
        Glide.with(this).load(getIntent().getStringExtra("VenueImage")).into(venueImage);
        venueGuest.setText(getIntent().getStringExtra("VenueGuest")+" People");
        venueRent.setText(getIntent().getStringExtra("VenueRent")+" Taka");
        eventType.setText(getIntent().getStringExtra("EventType"));

        addVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String venuename=getIntent().getStringExtra("VenueName");
                String venuelocation=getIntent().getStringExtra("VenueLocation");
                String venueimage=getIntent().getStringExtra("VenueImage");
                String venuebudget=getIntent().getStringExtra("VenueRent");
                String venueguest=getIntent().getStringExtra("VenueGuest");
                String venuetype=getIntent().getStringExtra("EventType");
                Venue venue=new Venue(venuename,venuelocation,venueimage);
                String key=null;
                SharedPreferences preferences=getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                if(preferences!=null)
                {
                    key=preferences.getString("Key","none").toString();
                }
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(key).child("Venue").setValue(venue).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Venue Added Successfully!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),infosActivity.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Venue could not be added",Toast.LENGTH_LONG).show();

                        }

                    }
                });

            }
        });

    }
}