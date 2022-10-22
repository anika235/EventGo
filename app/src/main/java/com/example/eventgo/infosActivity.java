package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class infosActivity extends AppCompatActivity {
    private Button button;
    private EditText locate, budget, guest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);
        button = (Button) findViewById(R.id.start);

        locate = findViewById(R.id.location);
        budget = findViewById(R.id.budget);
        guest = findViewById(R.id.guest);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextpage();
            }
        });
    }

    public void nextpage()
    {
        String loc = locate.getText().toString();
        String bud = budget.getText().toString();
        String gue = guest.getText().toString();

      String key=getIntent().getStringExtra("Key");
        HashMap<String,String> EventMap=new HashMap<String, String>();
        EventMap.put("location",loc);
        EventMap.put("budget",bud);
        EventMap.put("guest",gue);
            Event start = new Event(loc, bud, gue);
            FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Events").child(key).child("Infos").setValue(EventMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(), "Event setted up successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), homeFragment.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

    }


}