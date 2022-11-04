package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.eventgo.Adapter.CurrentBudgetAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CurrentBudget extends AppCompatActivity {

    private RecyclerView budgets;
    private DatabaseReference mReference;
    ArrayList<String> BudgetList;
    CurrentBudgetAdapter budgetAdapter;
    private TextView noResult;
    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_budget);

        budgets = (RecyclerView) findViewById(R.id.current_budget_list);
        String key=getIntent().getStringExtra("Event Key");
        mReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(key).child("EstimatedBudget");

        budgets.setHasFixedSize(true);
        budgets.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        BudgetList=new ArrayList<>();
        budgetAdapter=new CurrentBudgetAdapter(this,BudgetList);
        budgets.setAdapter(budgetAdapter);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String partbudget=dataSnapshot.getValue().toString();

                    if(partbudget!=null)
                    {
                        BudgetList.add(partbudget);
                    }

                }

                budgetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}