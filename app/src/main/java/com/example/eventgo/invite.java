package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class invite extends AppCompatActivity {

    TextView code;
    Button copybutton;
    Button sharebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        code = findViewById(R.id.code);
        code.setText(getIntent().getStringExtra("Event key"));
        copybutton = findViewById(R.id.copybutton);

        copybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("TextView", code.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                clipData.getDescription();
                Toast.makeText(getApplicationContext(),"Copied",Toast.LENGTH_SHORT).show();
            }
        });

        sharebutton = findViewById(R.id.sharebutton);
        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "I want to invite you to join my event using "+ code.getText().toString() +" and by downloading EventGo app and have a great time.";
                String Sharesub = "Join My event";
                intent.putExtra(Intent.EXTRA_SUBJECT, Sharesub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share Using"));

            }
        });

    }

}