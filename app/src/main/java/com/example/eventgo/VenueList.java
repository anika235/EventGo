package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VenueList extends AppCompatActivity {
    private RecyclerView venues;
    private DatabaseReference mVenueReference;
    ArrayList<Venue> venuesList;
    adapterVenue venueclass;
    private TextView noResult;
    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_list);

        noResult=(TextView)findViewById(R.id.noresult);
        venues = (RecyclerView) findViewById(R.id.venueList);
        mVenueReference = FirebaseDatabase.getInstance().getReference("Venues");


        venues.setHasFixedSize(true);
        venues.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        venuesList=new ArrayList<>();
        venueclass=new adapterVenue(this,venuesList);
        venues.setAdapter(venueclass);

        mVenueReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noResult.setVisibility(View.GONE);

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                   // noResult.setVisibility(View.GONE);
                    Venue venueDemo=dataSnapshot.getValue(Venue.class);
                    //assert venueDemo != null;
                    String tempVenue=venueDemo.location;
                    String venueLocation=getIntent().getStringExtra("Location");
                    if(venueLocation.isEmpty())
                    {
                        venueLocation=null;
                    }
                    int venueBudget;
                    if(getIntent().getStringExtra("Budget").isEmpty())
                    {
                         venueBudget=0;
                    }
                    else {
                         venueBudget = Integer.parseInt(getIntent().getStringExtra("Budget"));
                    }

                    if(venueDemo!=null && tempVenue.equalsIgnoreCase(venueLocation) || venueDemo.budget>(venueBudget-10000) && venueDemo.budget<(venueBudget+10000))
                    {
                        venuesList.add(venueDemo);
                    }

                }
                if(venuesList.size()==0)
                {
                    noResult.setVisibility(View.VISIBLE);
                }

                venueclass.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }




}