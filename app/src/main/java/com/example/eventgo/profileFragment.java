package com.example.eventgo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class profileFragment extends Fragment {
    TextView fname, lname, bdate, mail, number;
    Activity context;

    public profileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context =getActivity();
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        fname = view.findViewById(R.id.fname_profile);
        lname = view.findViewById(R.id.lname_profile);
        bdate = view.findViewById(R.id.bdate_profile);
        mail = view.findViewById(R.id.mail_profile);
        number =  view.findViewById(R.id.number_profile);

        setProperties();

       return view;
    }
    public void onStart() {

        super.onStart();

    }
    public void setProperties(){
        System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        FirebaseDatabase.getInstance().getReference("Users")
                .child((FirebaseAuth.getInstance().getCurrentUser().getUid()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists())
                        {
                            User user = snapshot.getValue(User.class);
                            if(user!= null)
                            {
                                System.out.println(user);
                                System.out.println(user.getLname());
                                lname.setText(user.getLname());
                                bdate.setText(user.getBdate());
                                mail.setText(user.getMail());
                                number.setText(user.getNumber());
                            }
                            else
                            {
                                System.out.println("Failed");
                            }

                        }
                        else
                        {
                            System.out.println("Failed");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

}