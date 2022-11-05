package com.example.eventgo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventgo.CurrentView;
import com.example.eventgo.Event;
import com.example.eventgo.R;

import java.util.ArrayList;

public class adapterPrev extends RecyclerView.Adapter<adapterPrev.MyviewHolder> {

    private Context context;
    private ArrayList<Event> list;

    public adapterPrev(Context context, ArrayList<Event>list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Event event = list.get(position);
        holder.eventname.setText(event.getTitle());
        holder.eventdate.setText(event.getImage());
        Glide.with(context).load(event.getImage()).into(holder.eventImage);
        holder.eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CurrentView.class);
                intent.putExtra("Event name", event.getTitle());
                intent.putExtra("Event date", event.getDate());
                intent.putExtra("Event key", event.getKey());
                intent.putExtra("Event Image",event.getImage());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder {
        TextView eventname, eventdate;
        ImageView eventImage;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            eventname = itemView.findViewById(R.id.eventTitle);
            eventdate = itemView.findViewById(R.id.eventDate);
            eventImage = itemView.findViewById(R.id.eventImage);
        }
    }
}
