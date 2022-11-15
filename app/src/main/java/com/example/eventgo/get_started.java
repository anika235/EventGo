package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class get_started extends AppCompatActivity {
    ViewPager viewPager;
    private Button button;
    CircleIndicator circleIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_started);

        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circleindicator);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.anikaa);
        imageList.add(R.drawable.anikaa2);
        imageList.add(R.drawable.anikaa3);
        adapterclass myadapter = new adapterclass(imageList);

        viewPager.setAdapter(myadapter);
        circleIndicator.setViewPager(viewPager);

        button =(Button) findViewById(R.id.getstartedbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openloginpage();
            }
        });
    }
    public void openloginpage(){
        Intent intent = new Intent(this, login_page.class);
        startActivity(intent);
    }

    }
