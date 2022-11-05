package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.example.eventgo.Adapter.ToDoadapter;
import com.example.eventgo.Model.checklist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class checklistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String code;
    private FloatingActionButton mFab;
    private DatabaseReference database;
    private ToDoadapter adapter;
    private List<checklist> mllist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        code = getIntent().getStringExtra("Event key");


        recyclerView = findViewById(R.id.checklist);
        mFab = findViewById(R.id.floatingActionButton);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(checklistActivity.this));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                myEdit.putString("Event key", code);

                myEdit.commit();

                AddnewTask.newInstance().show(getSupportFragmentManager(), AddnewTask.TAG);
            }
        });

        database = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(code).child("Checklist");
        mllist = new ArrayList<>();
        adapter =new ToDoadapter(checklistActivity.this, mllist);
        recyclerView.setAdapter(adapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        checklist check = dataSnapshot.getValue(checklist.class);
                        System.out.println(dataSnapshot.getValue());
                        mllist.add(check);
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