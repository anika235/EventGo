package com.example.eventgo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adaptercardview extends RecyclerView.Adapter<adaptercardview.myviewholder> {

    Context context;
    ArrayList<Event> list;
    TextView evettime;


    public adaptercardview(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        Event c = list.get(position);
        holder.eventname.setText(c.getTitle());
        holder.eventdate.setText(c.getDate());

        holder.eventname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CurrentView.class);
                intent.putExtra("Event name", c.getTitle());
                intent.putExtra("Event date", c.getDate());
                intent.putExtra("Event key" , c.getKey());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder{
        TextView eventname, eventdate;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            eventname = itemView.findViewById(R.id.eventname);
            eventdate = itemView.findViewById(R.id.eventdate);
        }
    }
}
