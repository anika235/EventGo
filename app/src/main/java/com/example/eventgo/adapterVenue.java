package com.example.eventgo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.crypto.Cipher;

public class adapterVenue extends RecyclerView.Adapter<adapterVenue.VenueViewHolder> {
    Context context;
    ArrayList<Venue> venueList;

    public  adapterVenue(Context context,ArrayList<Venue> venueList)
    {
        this.context=context;
        this.venueList=venueList;
    }

    @NonNull
    @Override
    public VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.location_layout,parent,false);
        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, int position) {

        Venue venue=venueList.get(position);
        holder.venueName.setText(venue.getName());
        Glide.with(context).load(venue.getImage()).into(holder.venueImage);
        holder.venueImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    //View Holder Class
    public static class VenueViewHolder extends RecyclerView.ViewHolder{
        ImageView venueImage;
        TextView venueName;

        public VenueViewHolder(View itemView) {
            super(itemView);
            venueName=itemView.findViewById(R.id.venueName);
           venueImage=itemView.findViewById(R.id.venueImage);
        }
        /*public void setDetails(Context ctx, String venue_name, String venue_image)
        {
            TextView venueName=(TextView) mView.findViewById(R.id.venueName);
            ImageView venueImage=(ImageView)mView. findViewById(R.id.venueImage);

            venueName.setText(venue_name);
            Glide.with(ctx).load(venue_image).into(venueImage);
        }*/


    }

}
