package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login_page extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        button =(Button) findViewById(R.id.loginbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openthirdpage();
            }
        });
    }
    public void openthirdpage(){
        Intent intent = new Intent(this, third_page.class);
        startActivity(intent);
    }

}