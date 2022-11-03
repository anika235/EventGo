package com.example.eventgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.time.Year;
import java.util.Calendar;

public class third_page extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button button;
    private EditText etdate;
    private EditText ettime;
    private EditText title, times, dates;
    public String typ;
    ImageView  upload_image;
    Uri filepath=null;
    String eventImage;
    Bitmap bitmap;
    private Button upload;
    Spinner type;
    int ethour, etmin;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_page);
        etdate = findViewById(R.id.dates);
        ettime = findViewById(R.id.times);
        button=(Button) findViewById(R.id.create);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {etdate.setShowSoftInputOnFocus(false);
            ettime.setShowSoftInputOnFocus(false);}
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        third_page.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date= day+"/"+month+"/"+year;
                        etdate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        Spinner spinner = findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Type, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("Event name");
        ettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        third_page.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                ethour = hour;
                                etmin = minute;
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(0, 0, 0, ethour, etmin);
                                ettime.setText(DateFormat.format("hh:mm aa", calendar1));
                            }
                        },12,0, false
                );
                timePickerDialog.updateTime(ethour, etmin);
                timePickerDialog.show();
            }
        });
        title = findViewById(R.id.title);
        times = findViewById(R.id.times);
        dates = findViewById(R.id.dates);
        type = (Spinner)findViewById(R.id.type);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextpage();
            }
        });

        upload_image=(ImageView) findViewById(R.id.upload_image);
        upload=(Button)findViewById(R.id.upload);

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(third_page.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent,1);
                        // Intent.createChooser(intent,"Please Select an Image")

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();

                    }
                }).check();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filepath!=null) {

                    uploadToFirebase();
                }


            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);

            filepath = data.getData();
           /* try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
            }
            catch (Exception ex)
            {
                Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG).show();
            }*/
            upload_image.setImageURI(filepath);
        }
    }


    private void uploadToFirebase() {
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();
        title=(EditText)findViewById(R.id.title);
        dates=(EditText)findViewById(R.id.dates);
        String gettitle=title.getText().toString();
        String getdate=dates.getText().toString();

        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploader= storage.getReference().child("Event Images").child(System.currentTimeMillis()+".jpg");
        uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();

                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {

                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful())
                                {
                                    eventImage = task.getResult().toString();
                                    Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_LONG).show();


                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Could not upload the Image",Toast.LENGTH_LONG).show();


                            }
                        });
                    }



                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded:"+(int)percent+"%");

                    }
                });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) {
            Toast.makeText(getApplicationContext(),"please select a type",Toast.LENGTH_SHORT).show();
            return;
        }
        String text = parent.getItemAtPosition(position).toString();
        typ=text;
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    public void nextpage()
    {
        String tit = title.getText().toString();
        String tim = times.getText().toString();
        String dat = dates.getText().toString();
        String[] values = dat.split("/");
        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);


        if(eventImage==null)
        {
            eventImage="https://firebasestorage.googleapis.com/v0/b/eventgo-ec09c.appspot.com/o/Event%20Images%2Feventimage.jpg?alt=media&token=dc0aa266-7ef5-43f6-95d9-647de0416814";
        }

        if(tit.isEmpty())
        {
            title.setError("Title is required");
            title.requestFocus();
        }
        else if(tim.isEmpty())
        {
            times.setError("Time is required");
            times.requestFocus();
        }
        else if(dat.isEmpty())
        {
            dates.setError("Date is required");
            dates.requestFocus();
        }
        else if(typ.isEmpty())
        {
            Toast.makeText(this,"Event Type is Required",Toast.LENGTH_LONG).show();
            type.requestFocus();
        }
        else
        {

            String key=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").push().getKey();
            Event event=new Event(tit,typ,dat,tim,key,eventImage);
            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events").child(key).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Event added Successfully",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),infosActivity.class);
                        intent.putExtra("Key",key);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}