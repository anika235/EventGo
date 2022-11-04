package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Budget_list extends AppCompatActivity {
    RecyclerView layoutlist;
    TextView expense;
    Button budgetadd;
    Button budget_save;
    int counter=0;
    private static ArrayList<BudgetModel> budgetList;
    BudgetModel budgetModel;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_list);
        budget_save=findViewById(R.id.budget_save);
        expense=findViewById(R.id.total_budget);
        expense.setText("0 Taka");


        budgetList=new ArrayList<>();


        RecyclerView recyclerView=findViewById(R.id.budget_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BudgetAdapter adapter=new BudgetAdapter(budgetList);
        recyclerView.setAdapter(adapter);
        expense.setText(String.valueOf(adapter.getExpenseFinalTotal()));
        Log.d("Expense",String.valueOf(adapter.getExpenseFinalTotal()));

        findViewById(R.id.budget_add).setOnClickListener(view -> {


            budgetModel=new BudgetModel(counter,"");
            counter++;
            budgetList.add(budgetModel);


            adapter.notifyItemInserted(budgetList.size());


        });
        budget_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key=getIntent().getStringExtra("Key");
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(key).child("EstimatedBudget").setValue(adapter.returnList()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Budget_list.this, "Budget saved", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(Budget_list.this,"Failed",Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });



    }
}