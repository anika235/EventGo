package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class second_page extends AppCompatActivity {
    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page);
        button1 =(Button) findViewById(R.id.createbutton);
        button2=(Button)findViewById(R.id.checkcurbutton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openthirdpage();
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view)
            {
                openCurrentEvent();
            }

        });
    }
    public void openthirdpage(){
        Intent intent = new Intent(this, third_page.class);
        startActivity(intent);
    }
    public void openCurrentEvent()
    {
        Intent intent=new Intent(this,CurrentEvent.class);
        startActivity(intent);
    }


}