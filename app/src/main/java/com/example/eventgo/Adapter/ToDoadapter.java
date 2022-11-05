package com.example.eventgo.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventgo.Model.checklist;
import com.example.eventgo.R;
import com.example.eventgo.checklistActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ToDoadapter extends RecyclerView.Adapter<ToDoadapter.MyviewHolder> {
    private List<checklist> todolist;
    private checklistActivity checkactivity;
    private FirebaseDatabase database;
    private String code;
    private Context context;

    public ToDoadapter(checklistActivity activity, List<checklist>todolist){
        this.todolist = todolist;
        checkactivity =activity;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(checkactivity).inflate(R.layout.list_task, parent, false);
       return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        checklist check = todolist.get(position);
        holder.checkbox.setText(check.getTask());
        holder.duedate.setText(check.getDueDate());
//        holder.checkbox.setChecked(toBoolean(check.getStatus()));
//        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(compoundButton.isChecked()){
//                    SharedPreferences preferences = context.getSharedPreferences("MySharedPref",context.MODE_PRIVATE);
//                    String code = preferences.getString("Event key", "");
//                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(code).child("Checklist")
//                            .child(code).setValue("1");
//                }
//                else {
//                    SharedPreferences preferences = context.getSharedPreferences("MySharedPref",context.MODE_PRIVATE);
//                    String code = preferences.getString("Event key", "");
//                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(code).child("Checklist")
//                            .child(code).setValue("0");
//
//                }
//            }
//        });

    }
//     private boolean toBoolean(String status)
//    {
//        return status != "0";
//    }

    @Override
    public int getItemCount() {
        return todolist.size();
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder{
        TextView duedate;
        CheckBox checkbox;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            duedate = itemView.findViewById(R.id.due_date);
            checkbox = itemView.findViewById(R.id.mcheckbox);


        }
    }
}
