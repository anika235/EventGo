package com.example.eventgo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventgo.Adapter.CurrentBudgetAdapter;
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

public class EstimateBudgetFragment extends Fragment {

    private FloatingActionButton fab;
    private TextView total_amount;
    private RecyclerView budgetRecycle;


    private DatabaseReference budgetReference;
    private FirebaseAuth mAuth;
    private ProgressDialog loader;

    private String post_key="";
    private String item="";
    private int amount=0;


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
        context=getActivity();
        View mainView=inflater.inflate(R.layout.fragment_estimate_budget, container, false);


        return mainView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String key=context.getIntent().getStringExtra("Event Key");

        fab= context.findViewById(R.id.add_new_item);
        mAuth=FirebaseAuth.getInstance();
        budgetReference= FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Events").child(key).child("EstimatedBudget");
        loader=new ProgressDialog(getActivity());

        total_amount=context.findViewById(R.id.estimated_budget);
        budgetRecycle=context.findViewById(R.id.estimated_budget_recycle);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
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

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<BudgetModel> options=new FirebaseRecyclerOptions.Builder<BudgetModel>().setQuery(budgetReference,BudgetModel.class).build();



        FirebaseRecyclerAdapter<BudgetModel, Budget_list.budgetViewHolder> adapter=new FirebaseRecyclerAdapter<BudgetModel, Budget_list.budgetViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Budget_list.budgetViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull BudgetModel model) {

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
            public Budget_list.budgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_retrieve,parent,false);
                return new Budget_list.budgetViewHolder(view);
            }
        };

        budgetRecycle.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();

    }
    private void additem() {

        AlertDialog.Builder mydialog=new AlertDialog.Builder(context);
        LayoutInflater inflater= LayoutInflater.from(context);

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
                                Toast.makeText(context,"Budget Added Successfully",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(context,task.getException().toString(),Toast.LENGTH_LONG).show();

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



    private void updateBudget() {

        AlertDialog.Builder myDialog=new AlertDialog.Builder(context);
        LayoutInflater inflater= LayoutInflater.from(context);

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
                            Toast.makeText(context,"Updated Successfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(context,task.getException().toString(),Toast.LENGTH_LONG).show();

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
                            Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(context,task.getException().toString(),Toast.LENGTH_LONG).show();

                        }


                    }
                });
                dialog.dismiss();

            }
        });


        dialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();


        String key=context.getIntent().getStringExtra("Event Key");

        fab= context.findViewById(R.id.add_new_item);
        mAuth=FirebaseAuth.getInstance();
        budgetReference= FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Events").child(key).child("EstimatedBudget");
        loader=new ProgressDialog(getActivity());

        total_amount=context.findViewById(R.id.estimated_budget);
        budgetRecycle=context.findViewById(R.id.estimated_budget_recycle);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
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

        super.onStart();
        FirebaseRecyclerOptions<BudgetModel> options=new FirebaseRecyclerOptions.Builder<BudgetModel>().setQuery(budgetReference,BudgetModel.class).build();



        FirebaseRecyclerAdapter<BudgetModel, Budget_list.budgetViewHolder> adapter=new FirebaseRecyclerAdapter<BudgetModel, Budget_list.budgetViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Budget_list.budgetViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull BudgetModel model) {

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
            public Budget_list.budgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_retrieve,parent,false);
                return new Budget_list.budgetViewHolder(view);
            }
        };

        budgetRecycle.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();





    }
}