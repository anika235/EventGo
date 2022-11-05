package com.example.eventgo.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventgo.GuestActivity;
import com.example.eventgo.Model.checklist;
import com.example.eventgo.Model.guestlist;
import com.example.eventgo.R;
import com.example.eventgo.checklistActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class guestlistadapter extends RecyclerView.Adapter<guestlistadapter.MyviewHolder> {

    private List<guestlist> glist;
    private GuestActivity guestActivity;
    private FirebaseDatabase database;
    private String code;
    private Context context;

    public guestlistadapter(GuestActivity activity, List<guestlist> guestlist){
        this.glist = guestlist;
        guestActivity =activity;
        context = activity;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(guestActivity).inflate(R.layout.list_guest, parent, false);
       return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        guestlist guestl = glist.get(position);
        holder.guestbox.setText(guestl.getName());
        holder.mails.setText(guestl.getMail());
        holder.guestbox.setChecked(toBoolean(guestl.getStatus()));
        holder.guestbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = context.getSharedPreferences("MySharedPref",context.MODE_PRIVATE);
                    String code = preferences.getString("Event key", "");
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(code).child("Guestlist")
                            .child(guestl.getKey()).child("status").setValue("1");
                }
                else {
                    SharedPreferences preferences = context.getSharedPreferences("MySharedPref",context.MODE_PRIVATE);
                    String code = preferences.getString("Event key", "");
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(code).child("Guestlist")
                            .child(guestl.getKey()).child("status").setValue("0");

                }
            }
        });
    }
    private boolean toBoolean(String status)
    {
        return !status.equals("0");
    }

    @Override
    public int getItemCount() {
        return glist.size();
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder{
        TextView mails;
        CheckBox guestbox;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            mails = itemView.findViewById(R.id.mails);
            guestbox = itemView.findViewById(R.id.mguestbox);

        }
    }
}
