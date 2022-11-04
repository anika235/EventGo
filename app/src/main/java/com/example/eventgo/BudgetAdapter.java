package com.example.eventgo;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

    ArrayList<String> expNameArray=new ArrayList<>();
    ArrayList<String> expAmtArray=new ArrayList<>();
    boolean isOnTextChanged=false;
    int ExpenseFinaltotal=0;




    int counter=0;

    HashMap<String,String> EstimatedBudget=new HashMap<>();


    public BudgetAdapter(ArrayList<BudgetModel> items) {
        this.items = items;
    }

    ArrayList<BudgetModel> items;

    @NonNull
    @Override
    public budgetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_budget,parent,false);
        return new budgetHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull budgetHolder holder, @SuppressLint("RecyclerView") int position) {
       // holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
       int id=items.get(position).getExpenseId();

        counter++;

        holder.section.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                items.get(position).setExpenseName(holder.section.getText().toString());


            }
        });
        holder.money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isOnTextChanged=true;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(isOnTextChanged)
                {
                    isOnTextChanged=false;
                }
               /* try{
                    Log.d("IDddd",String.valueOf(id));
                    ExpenseFinalTotal=0;
                    for(int i=0;i<=id;i++)
                    {
                        if(i!=id)
                        {
                            expNameArray.add("0");
                            expNameArray.add("0");
                        }
                        else
                        {
                            expAmtArray.add("0");
                            expAmtArray.add("0");
                          expNameArray.set(id,items.get(position).getExpenseName());
                            expAmtArray.set(id,editable.toString());
                        }
                    }
                    Log.d("ExmpAmt",expAmtArray.toString());
                    Log.d("ExpName",expNameArray.toString());



                }catch (NumberFormatException e)
                {

                }*/
               ExpenseFinaltotal=ExpenseFinaltotal+Integer.parseInt(holder.money.getText().toString());
               setExpense(ExpenseFinaltotal);



                EstimatedBudget.put(String.valueOf(counter),holder.section.getText().toString() + " :   "+holder.money.getText().toString());
                




            }
        });
       // EstimatedBudget.put(String.valueOf(counter),holder.section.getText().toString());

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int postition)
    {
        return (long) postition;
    }



    public HashMap<String,String> returnList()
    {
        return EstimatedBudget;
    }
    public  void setExpense(int ExpenseFinaltotal)
    {
        this.ExpenseFinaltotal=ExpenseFinaltotal;
    }

    public int getExpenseFinalTotal()
    {
        return this.ExpenseFinaltotal;
    }


   /* @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        ((RecyclerView.ViewHolder) holder).enableTextWatcher();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        ((RecyclerView.ViewHolder) holder).disableTextWatcher();
    }*/


}
class budgetHolder extends RecyclerView.ViewHolder{
    int counter=0;
    EditText section,money;
    private BudgetAdapter adapter;
    ImageView delete;



    public budgetHolder(@NonNull View itemView){
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

