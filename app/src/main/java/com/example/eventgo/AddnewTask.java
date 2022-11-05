package com.example.eventgo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eventgo.Model.checklist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AddnewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddnewTask";
    private TextView setDueDate;
    private EditText taskEdit;
    private Button Savebtn;
    private FirebaseDatabase database;
    private Context context;
    private String dueDate = "";

    public  static AddnewTask newInstance(){
        return new AddnewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task,container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDueDate = view.findViewById(R.id.set_due_tv);
        taskEdit = view.findViewById(R.id.task_edittext);
        Savebtn = view.findViewById(R.id.savetask);

        database = FirebaseDatabase.getInstance();

        taskEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    Savebtn.setEnabled(false);
                    Savebtn.setBackgroundColor(Color.GRAY);
                }else{
                    Savebtn.setEnabled(true);
                    Savebtn.setBackgroundColor(getResources().getColor(R.color.color3));
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int MONTH = calendar.get(Calendar.MONTH);
                int YEAR = calendar.get(Calendar.YEAR);
                int DAY = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                        month = month +1;
                        setDueDate.setText(dayofmonth + "/" + month +  "/" + year);
                        dueDate = dayofmonth + "/" + month +  "/" + year;
                    }
                },YEAR, MONTH, DAY);
                datePickerDialog.show();
            }
        });
        Savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = taskEdit.getText().toString();
                if(task.isEmpty()){
                    Toast.makeText(context, "Empty task not allowed!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String key=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").push().getKey();

                    SharedPreferences preferences = context.getSharedPreferences("MySharedPref",context.MODE_PRIVATE);
                    String code = preferences.getString("Event key", "");

                    HashMap<String, String> Checkmap = new HashMap<>();
                    Checkmap.put("task",task);
                    Checkmap.put("dueDate",dueDate);
                    Checkmap.put("status","0");
                    Checkmap.put("key", key);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(code).child("Checklist")
                            .child(key).setValue(Checkmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(context, "Task saved", Toast.LENGTH_SHORT).show();
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
        if(activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).OnDialogClose(dialog);
        }
    }
}
