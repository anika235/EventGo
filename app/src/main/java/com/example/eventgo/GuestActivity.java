package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.eventgo.Adapter.guestlistadapter;
import com.example.eventgo.Model.checklist;
import com.example.eventgo.Model.guestlist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GuestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String code;
    private FloatingActionButton mFab;
    private DatabaseReference database;
    private guestlistadapter adapter;
    private List<guestlist> glist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        code = getIntent().getStringExtra("Event key");

        recyclerView = findViewById(R.id.guestlist);
        mFab = findViewById(R.id.Button2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(GuestActivity.this));
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                myEdit.putString("Event key", code);

                myEdit.commit();
                AddnewGuest.newInstance().show(getSupportFragmentManager(), AddnewGuest.TAG);

            }
        });

        database = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Events").child(code).child("Guestlist");
        glist = new ArrayList<>();
        adapter = new guestlistadapter(GuestActivity.this, glist);
        recyclerView.setAdapter(adapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        guestlist guestl = dataSnapshot.getValue(guestlist.class);
                        System.out.println(dataSnapshot.getValue());
                        glist.add(guestl);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}