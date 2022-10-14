package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Thread thread = new Thread(){
            public void run()
            {
                try{
                    sleep(3000);
                }
                catch (Exception e){
                    e.printStackTrace();

                }
                finally {
                    Intent intent = new Intent(splashscreen.this, get_started.class);
                    startActivity(intent);
                    finish();
                }

            }
        };thread.start();
    }
}