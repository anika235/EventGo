package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Budget_list extends AppCompatActivity {
    private FloatingActionButton fab;
    private TextView total_amount;
    private RecyclerView budgetRecycle;


    private DatabaseReference budgetReference;
    private FirebaseAuth mAuth;
    private ProgressDialog loader;

    private String post_key="";
    private String item="";
    private int amount=0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_list);
        String key=getIntent().getStringExtra("Key");

        fab= findViewById(R.id.add_new_item);
        mAuth=FirebaseAuth.getInstance();
        budgetReference=FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Events").child(key).child("EstimatedBudget");
        loader=new ProgressDialog(this);


        total_amount=findViewById(R.id.total_budget);
        budgetRecycle=findViewById(R.id.budget_recyle);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        budgetRecycle.setHasFixedSize(true);
        budgetRecycle.setLayoutManager(linearLayoutManager);

        budgetReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int totalAmount=0;
                for(DataSnapshot snap:snapshot.getChildren())
                {
                    BudgetModel budgetModel=snap.getValue(BudgetModel.class);
                    totalAmount+=budgetModel.getAmount();
                    String sTotal=String.valueOf(totalAmount+" Taka");
                    total_amount.setText(sTotal);


                }
                FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Events").child(key).child("Total Budget").setValue(totalAmount);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additem();

            }
        });





    }



    private void additem() {
        AlertDialog.Builder mydialog=new AlertDialog.Builder(this);
        LayoutInflater inflater= LayoutInflater.from(this);

        View myview=inflater.inflate(R.layout.budget_layout,null);
        mydialog.setView(myview);
        mydialog.setCancelable(false);

        final AlertDialog dialog=mydialog.create();




        final EditText budgetItem=myview.findViewById(R.id.budget_item);
        final EditText budgetAmount=myview.findViewById(R.id.amount);
        final Button cancel=myview.findViewById(R.id.cancel);
        final Button save =myview.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String budgetSection=budgetItem.getText().toString();
                String budgetamount=budgetAmount.getText().toString();

                if(budgetSection.isEmpty())
                {
                    budgetItem.setError("Please enter the expenditure section");
                    return;
                }
                if(budgetamount.isEmpty())
                {
                    budgetAmount.setError("Amount is required");
                    return;
                }

                else
                {
                    loader.setMessage("Adding a budget item");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    String id=budgetReference.push().getKey();

                    DateFormat dateFormat=new SimpleDateFormat("dd-MM-yy");
                    Calendar cal=Calendar.getInstance();
                    String date=dateFormat.format(cal.getTime());

                    MutableDateTime epoch=new MutableDateTime();
                    epoch.setDate(0);
                    DateTime now=new DateTime();
                    Months months= Months.monthsBetween(epoch, now);

                    BudgetModel budgetPart=new BudgetModel(budgetSection,date,id,null,Integer.parseInt(budgetamount),months.getMonths());
                    budgetReference.child(id).setValue(budgetPart).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Budget_list.this,"Budget Added Successfully",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(Budget_list.this,task.getException().toString(),Toast.LENGTH_LONG).show();

                            }
                            loader.dismiss();

                        }
                    });
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<BudgetModel> options=new FirebaseRecyclerOptions.Builder<BudgetModel>().setQuery(budgetReference,BudgetModel.class).build();

        FirebaseRecyclerAdapter<BudgetModel,budgetViewHolder> adapter=new FirebaseRecyclerAdapter<BudgetModel, budgetViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull budgetViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull BudgetModel model) {

                holder.setItemAmount("Allocated Amount :   "+model.getAmount());
                holder.setItemDate("On :   "+ model.getDate());
                holder.setItemname("Budget Section  :   "+model.getItem());

                holder.notes.setVisibility(View.GONE);

                holder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        post_key=getRef(position).getKey();
                        item=model.getItem();
                        amount=model.getAmount();
                        updateBudget();
                    }
                });



            }

            @NonNull
            @Override
            public budgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_retrieve,parent,false);
                return new budgetViewHolder(view);
            }
        };

        budgetRecycle.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }



    public static class budgetViewHolder extends RecyclerView.ViewHolder{

        View mview;
        public TextView notes;

        public budgetViewHolder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;
            notes=itemView.findViewById(R.id.note);
        }

        public void setItemname(String itemName)
        {
            TextView item=mview.findViewById(R.id.item);
            item.setText(itemName);
        }
        public void setItemAmount(String itemAmount)
        {
            TextView amount=mview.findViewById(R.id.amount);
            amount.setText(itemAmount);
        }

        public void setItemDate(String itemDate)
        {
            TextView date =mview.findViewById(R.id.date);
            date.setText(itemDate);
        }


    }

    private void updateBudget() {

        AlertDialog.Builder myDialog=new AlertDialog.Builder(this);
        LayoutInflater inflater= LayoutInflater.from(this);

        View myview=inflater.inflate(R.layout.update_budget,null);
        myDialog.setView(myview);
        myDialog.setCancelable(false);

        final AlertDialog dialog=myDialog.create();

        final TextView mItem=myview.findViewById(R.id.itemName);
        final EditText mAmount=myview.findViewById(R.id.amount);
        final EditText mNotes=myview.findViewById(R.id.note);

        mNotes.setVisibility(View.GONE);

        mItem.setText(item);

        mAmount.setText(String.valueOf(amount));
        mAmount.setSelection(String.valueOf(amount).length());

        Button delBut=myview.findViewById(R.id.btnDelete);
        Button btnUpdate=myview.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount=Integer.parseInt(mAmount.getText().toString());

                DateFormat dateFormat=new SimpleDateFormat("dd-MM-yy");
                Calendar cal=Calendar.getInstance();
                String date=dateFormat.format(cal.getTime());

                MutableDateTime epoch=new MutableDateTime();
                epoch.setDate(0);
                DateTime now=new DateTime();
                Months months= Months.monthsBetween(epoch, now);

                BudgetModel budgetPart=new BudgetModel(item,date,post_key,null,amount,months.getMonths());
                budgetReference.child(post_key).setValue(budgetPart).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Budget_list.this,"Updated Successfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(Budget_list.this,task.getException().toString(),Toast.LENGTH_LONG).show();

                        }


                    }
                });
                dialog.dismiss();
            }
        });

        delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                budgetReference.child(post_key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Budget_list.this,"Deleted Successfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(Budget_list.this,task.getException().toString(),Toast.LENGTH_LONG).show();

                        }


                    }
                });
                dialog.dismiss();

            }
        });


        dialog.show();
    }
}