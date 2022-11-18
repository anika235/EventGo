package com.example.eventgo;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class CurrentVenue extends AppCompatActivity {
    TextView venuetitle,venueplace;
    ImageView venuephoto;
    Button changeVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_venue);
        venuetitle=(TextView) findViewById(R.id.venuetitle);
        venueplace=(TextView)findViewById(R.id.venuelocation);
        venuephoto=(ImageView)findViewById(R.id.venuephoto);
        changeVenue=(Button)findViewById(R.id.changeVenue);


            String key = getIntent().getStringExtra("Event Key");
            //System.out.println(key+ " "+ FirebaseAuth.getInstance().getCurrentUser().getUid());
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(key).child("Venue").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Venue venue = snapshot.getValue(Venue.class);
                        if (venue != null) {
                            if(!venue.getName().isEmpty()) {

                                venuetitle.setText(venue.getName());
                            }
                            if(!venue.getLocation().isEmpty()) {
                                venueplace.setText(venue.getLocation());
                            }
                            if(!venue.getImage().isEmpty()) {
                                Glide.with(getApplicationContext()).load(venue.getImage()).into(venuephoto);
                            }

                        } else {
                            System.out.println("Failed");
                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            changeVenue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),AddLocation.class);
                    intent.putExtra("Key",getIntent().getStringExtra("Event Key"));
                    startActivity(intent);

                }
            });

    }
}