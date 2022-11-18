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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddLocation extends AppCompatActivity {
    EditText venue;
    EditText desiredVenue;
    EditText expectBudget;
    Button addVenue;
    Button searchVenue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        venue=(EditText) findViewById(R.id.venue);
        addVenue=(Button) findViewById(R.id.add);
        searchVenue=(Button)findViewById(R.id.search);

        addVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = getIntent().getStringExtra("Key");

                String ven = venue.getText().toString();
                if (ven.isEmpty()) {
                    venue.setError("Venue Name is Required");
                    return;
                }
                Venue venu=new Venue(ven,"","","",0,0);
                FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Events").child(key).child("Venue").setValue(venu).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            String back=getIntent().getStringExtra("back");
                            Toast.makeText(getApplicationContext(),"Venue Added Successfully!",Toast.LENGTH_LONG).show();
                            Intent intent;
                            if(back.equals("EventDetails"))
                            {
                                intent=new Intent(getApplicationContext(),CurrentVenue.class);
                            }
                            else
                            {
                                intent = new Intent(getApplicationContext(), infosActivity.class);
                            }
                            intent.putExtra("Key",key);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Venue could not be added",Toast.LENGTH_LONG).show();

                        }

                    }


                });

            }
        });

        searchVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key=getIntent().getStringExtra("Key");

                desiredVenue=(EditText)findViewById(R.id.LocationDesired);
                expectBudget=(EditText)findViewById(R.id.budgetForVenue);
                SharedPreferences preference = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                preference.edit().putString("Key",key).apply();


                Intent intent=new Intent(getApplicationContext(),VenueList.class);
                intent.putExtra("Location",desiredVenue.getText().toString());
                intent.putExtra("Budget",expectBudget.getText().toString());
                intent.putExtra("Key",getIntent().getStringExtra("Key"));
                startActivity(intent);

            }
        });


    }
}