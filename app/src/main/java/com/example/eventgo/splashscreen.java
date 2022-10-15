package com.example.eventgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        startActivity(new Intent(splashscreen.this,second_page.class));

                    }
                    else {
                        Intent intent = new Intent(splashscreen.this, get_started.class);
                        startActivity(intent);
                    }
                    finish();
                }

            }
        };thread.start();
    }
}