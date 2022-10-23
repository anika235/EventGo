package com.example.eventgo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class homeFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference database;
    adaptercardview a;
    ArrayList<Event> list;

    Activity context;
    public homeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context =getActivity();

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onStart()
    {
        super.onStart();
        Button button = (Button) context.findViewById(R.id.createbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,third_page.class);
                startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
            }
        });

        recyclerView = (RecyclerView) context.findViewById(R.id.recylelist);
        database = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        list = new ArrayList<>();
        a = new adaptercardview(context, list);
        recyclerView.setAdapter(a);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                        Event event=dataSnapshot.getValue(Event.class);
                        if(event!=null)
                        {
                            list.add(event);
                        }
                }
                a.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}