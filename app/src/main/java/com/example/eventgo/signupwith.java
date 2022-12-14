package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

public class signupwith extends AppCompatActivity {
    private LinearLayout layout1;
    private TextView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupwith);
        layout1 = (LinearLayout) findViewById(R.id.continuephone);
        button = (TextView) findViewById(R.id.alreadyaccount);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openloginpage();
            }
        });

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensignuppage();
            }
        });

    }
    public void opensignuppage()
    {
        Intent intent1 = new Intent(this, signup_page.class);
        startActivity(intent1);
    }
    public void openloginpage(){
        Intent intent = new Intent(this, login_page.class);
        startActivity(intent);
    }
}