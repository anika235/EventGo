package com.example.eventgo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventgo.CurrentBudget;
import com.example.eventgo.R;
import com.example.eventgo.Venue;
import com.example.eventgo.VenueDetails;
import com.example.eventgo.adapterVenue;

import java.util.ArrayList;

public class CurrentBudgetAdapter extends RecyclerView.Adapter<CurrentBudgetAdapter.CurrentBudgetViewHolder> {
    Context context;
    ArrayList<String> BudgetList;

    public  CurrentBudgetAdapter(Context context,ArrayList<String> BudgetList)
    {
        this.context=context;
        this.BudgetList=BudgetList;
    }

    @NonNull
    @Override
    public CurrentBudgetAdapter.CurrentBudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.budget,parent,false);
        return new CurrentBudgetAdapter.CurrentBudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentBudgetViewHolder holder, int position) {
        holder.Budget.setText(BudgetList.get(position));



    }












    @Override
    public int getItemCount() {
        return BudgetList.size();
    }

    //View Holder Class
    public static class CurrentBudgetViewHolder extends RecyclerView.ViewHolder{

        TextView Budget;

        public CurrentBudgetViewHolder(View itemView) {
            super(itemView);

            Budget=itemView.findViewById(R.id.budget_part);
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
