package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class login_page extends AppCompatActivity {
    private Button button;
    private TextView button2;
    private TextView button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        button2 = (TextView) findViewById(R.id.signup);
        button =(Button) findViewById(R.id.loginbutton);
        button3 = (TextView) findViewById(R.id.forgotpassword);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensecondpage();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensignupwithpage();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openforgotpage(); }
        });
    }
    public void opensecondpage(){
        Intent intent = new Intent(this, second_page.class);
        startActivity(intent);
    }
    public void opensignupwithpage()
    {
        Intent intent1 = new Intent(this, signupwith.class);
        startActivity(intent1);
    }
    public void openforgotpage()
    {
        Intent intent2 = new Intent(this, forgotpass.class);
        startActivity(intent2);
    }


}