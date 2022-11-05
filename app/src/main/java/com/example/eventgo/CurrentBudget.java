package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.eventgo.Adapter.CurrentBudgetAdapter;
import com.example.eventgo.databinding.ActivityCurrentBudgetBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CurrentBudget extends AppCompatActivity {

    //    private RecyclerView budgets;
//    private DatabaseReference mReference;
//    ArrayList<String> BudgetList;
//    CurrentBudgetAdapter budgetAdapter;
//    private TextView noResult;
//    Activity context;
    private ActivityCurrentBudgetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrentBudgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(savedInstanceState==null) {

            replaceFragment(new EstimateBudgetFragment());
        }

        binding.bottomBudgetView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.estimate_budget:
                    replaceFragment(new EstimateBudgetFragment());
                    break;
                case R.id.spent_budget:
                    replaceFragment(new SpentbudgetFragment());
                    break;
            }
            return true;
        });


    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}