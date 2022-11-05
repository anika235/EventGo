package com.example.eventgo;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventgo.Adapter.CurrentBudgetAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class EstimateBudgetFragment extends Fragment {

    private RecyclerView budgets;
    private DatabaseReference mReference;
    private ArrayList<String> BudgetList;
    private CurrentBudgetAdapter budgetAdapter;
    private TextView noResult;
    private Activity context;

    public EstimateBudgetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estimate_budget, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
//        budgets = (RecyclerView) findViewById(R.id.current_budget_list);
//        String key=getIntent().getStringExtra("Event Key");
//        mReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(key).child("EstimatedBudget");
//
//        budgets.setHasFixedSize(true);
//        budgets.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//
//        BudgetList=new ArrayList<>();
//        budgetAdapter=new CurrentBudgetAdapter(this,BudgetList);
//        budgets.setAdapter(budgetAdapter);
//
//        mReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                for(DataSnapshot dataSnapshot:snapshot.getChildren())
//                {
//                    String partbudget=dataSnapshot.getValue().toString();
//
//                    if(partbudget!=null)
//                    {
//                        BudgetList.add(partbudget);
//                    }
//
//                }
//
//                budgetAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }
}