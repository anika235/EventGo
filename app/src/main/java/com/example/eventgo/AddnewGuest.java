package com.example.eventgo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddnewGuest extends BottomSheetDialogFragment {

    public static final String TAG = "AddnewGuest";
    private EditText saveGmails;
    private EditText guest_edit;
    private Button Savebtn;
    private FirebaseDatabase database;
    private Context context;

    public static AddnewGuest newInstance(){
        return new AddnewGuest();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_guest,container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveGmails = view.findViewById(R.id.set_gmail);
        guest_edit = view.findViewById(R.id.task_editguest);
        Savebtn = view.findViewById(R.id.saveguest);

        database= FirebaseDatabase.getInstance();

        guest_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    Savebtn.setEnabled(false);
                    Savebtn.setBackgroundColor(Color.GRAY);
                }else
                {
                    Savebtn.setEnabled(true);
                    Savebtn.setBackgroundColor(getResources().getColor(R.color.color3));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = guest_edit.getText().toString();
                String mails = saveGmails.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(context, "Empty credential not allowed!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String key=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").push().getKey();

                    SharedPreferences preferences = context.getSharedPreferences("MySharedPref",context.MODE_PRIVATE);
                    String code = preferences.getString("Event key", "");

                    HashMap<String, String> Guestmap = new HashMap<>();
                    Guestmap.put("name",name);
                    Guestmap.put("mail",mails);
                    Guestmap.put("status","0");
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(code).child("Guestlist")
                            .child(key).setValue(Guestmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(context, "Guest List Updates", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    dismiss();

                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof OnDialogCloseListener)
        {
            ((OnDialogCloseListener)activity).OnDialogClose(dialog);
        }
    }
}
