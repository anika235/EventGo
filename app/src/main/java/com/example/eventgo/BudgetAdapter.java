package com.example.eventgo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BudgetAdapter extends  RecyclerView.Adapter<budgetHolder>{
    int counter=0;

    HashMap<String,String> EstimatedBudget=new HashMap<>();

    public BudgetAdapter(List<String> items) {
        this.items = items;
    }

    List<String> items;

    @NonNull
    @Override
    public budgetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_budget,parent,false);
        return new budgetHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull budgetHolder holder, int position) {
        counter++;
        EstimatedBudget.put(String.valueOf(counter),holder.money.getText().toString());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public HashMap<String,String> returnList()
    {
        return EstimatedBudget;
    }

}
class budgetHolder extends RecyclerView.ViewHolder{
    int counter=0;
    EditText section,money;
    private BudgetAdapter adapter;
    ImageView delete;

    public budgetHolder(@NonNull View itemView) {
        super(itemView);

        section=itemView.findViewById(R.id.budget_section);
        money=itemView.findViewById(R.id.budget_money);


        itemView.findViewById(R.id.image_remove).setOnClickListener(view -> {
            adapter.items.remove(getAbsoluteAdapterPosition());
            adapter.notifyItemRemoved(getAbsoluteAdapterPosition());

        });
    }

    public budgetHolder linkAdapter(BudgetAdapter adapter)
    {
        this.adapter=adapter;
        return this;
    }

}

