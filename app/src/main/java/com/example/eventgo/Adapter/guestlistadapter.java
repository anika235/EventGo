package com.example.eventgo.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventgo.GuestActivity;
import com.example.eventgo.Model.checklist;
import com.example.eventgo.Model.guestlist;
import com.example.eventgo.R;
import com.example.eventgo.checklistActivity;
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
        holder.mails.setText(guestl.getMails());
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
