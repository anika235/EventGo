package com.example.eventgo;

import static android.content.ContentValues.TAG;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.auth.api.identity.SignInCredential;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInApi;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_page extends AppCompatActivity {
    private EditText email,password;
    private Button button;
    private TextView button2;
    private TextView button3;
    private LinearLayout button4;

    GoogleSignInClient mGoogleSigninClient;
    private FirebaseAuth mAuth;

    ImageView imageViewshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);


        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.confirm_password);

        button2 = (TextView) findViewById(R.id.signup);
        button =(Button) findViewById(R.id.loginbutton);
        button3 = (TextView) findViewById(R.id.forgotpassword);
        button4=(LinearLayout)findViewById(R.id.continuegoogle);
        mAuth=FirebaseAuth.getInstance();

        processrequest();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_login();
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
        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                google_login();
            }

        });

        imageViewshow = findViewById(R.id.eye);
        imageViewshow.setImageResource(R.drawable.hide_password);
        imageViewshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewshow.setImageResource(R.drawable.hide_password);
                }
                else
                {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewshow.setImageResource(R.drawable.show_pass);

                }
            }
        });

    }

    private void processrequest() {
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail().build();
        mGoogleSigninClient= GoogleSignIn.getClient(this,gso);

    }

    public void opensecondpage(){
        FirebaseDatabase.getInstance().getReference("Users")
                .child((FirebaseAuth.getInstance().getCurrentUser().getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        globalVar.currentUser = snapshot.getValue(User.class);
                        Intent intent = new Intent(login_page.this, second_page.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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

    public void google_login()
    {

        //Api.Client mGoogleSigninClient = null;
        Intent signinIntent=mGoogleSigninClient.getSignInIntent();
        startActivityForResult(signinIntent,101);
    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
           // GoogleSignInAccount GoogleSignIn = null;
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);



            try {


                GoogleSignInAccount account=task.getResult(ApiException.class);
                firebaseAuthwithGoogle(account.getIdToken());

            } catch (ApiException e) {
                Toast.makeText(this,"Error Getting Information",Toast.LENGTH_LONG).show();
                // ...
            }

        }
    }



    public void user_login()
    {
        String mail=email.getText().toString();
        String pass=password.getText().toString();

        if(mail.isEmpty())
        {
            email.setError("Please Enter Your Email");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            email.setError("Please Enter a valid Email Address");
            email.requestFocus();
            return;

        }
        if(pass.isEmpty())
        {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            password.setError("Password must be at least six characters long");
            password.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    opensecondpage();
                }
                else
                {
                    Toast.makeText(login_page.this,"Failed to login",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void firebaseAuthwithGoogle(String idtoken) {
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idtoken, null);
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(),second_page.class));


                        } else {
                            Toast.makeText(getApplicationContext(),"Authentication failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }


}