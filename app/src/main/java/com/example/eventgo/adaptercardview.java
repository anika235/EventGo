package com.example.eventgo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.operation.Overwrite;

import java.util.ArrayList;

public class adaptercardview extends RecyclerView.Adapter<adaptercardview.myviewholder> {

    Context context;
    ArrayList<Event> list;

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

    public void deleteitem(int position) {
        list.remove(position);
        notifyItemRemoved(position);

    }
    public Event getmyitem(int position)
    {
        Event event = list.get(position);
        return event;
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        Event c = list.get(position);
        holder.eventname.setText(c.getTitle());
        holder.eventdate.setText(c.getDate());
        Glide.with(context).load(c.getImage()).into(holder.eventImage);
        holder.eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CurrentView.class);
                intent.putExtra("Event name", c.getTitle());
                intent.putExtra("Event date", c.getDate());
                intent.putExtra("Event key", c.getKey());
                intent.putExtra("Event Image",c.getImage());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder {
        TextView eventname, eventdate;
        ImageView eventImage;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            eventname = itemView.findViewById(R.id.eventTitle);
            eventdate = itemView.findViewById(R.id.eventDate);
            eventImage=itemView.findViewById(R.id.eventImage);

        }
    }
}
