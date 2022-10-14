package com.example.eventgo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class homeFragment extends Fragment {
    ListView listView;
    Activity context;
    String[] events = {"Science Conference 2022   25/08/2022", "Birthday  20/09/2022", "wedding  4/10/2022", "wedding 20/10/2022", "concert 03/12/2022", "Nobinboron 14/09/2022",
                      "biye 03/09/2022", "hello 2000", "amajjjmcfe","ureghrtg", "vuifjgrgurg"};

    public homeFragment() {
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
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onStart()
    {
        super.onStart();
        Button button = (Button) context.findViewById(R.id.createbutton);
        //TextView button2 =(TextView) context.findViewById(R.id.textView3) ;
        listView = (ListView) context.findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, events);
        listView.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,third_page.class);
                startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, events[i], Toast.LENGTH_SHORT).show();
                 if(i==0)
                 {
                     startActivity(new Intent(context, CurrentView.class));
                 }
            }
        });


    }
}