package com.example.myapplication12;



import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText editEmail,editPassword;
    private Button btnsignin;
    TextView forgotTextLink;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.txtEmail);
        editPassword =  findViewById(R.id.txtPwd);
        forgotTextLink = findViewById(R.id.forgotPassword);


        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");

        btnsignin = findViewById(R.id.btnLogin);
        TextView register = (TextView)findViewById(R.id.lnkRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Registration.class) );
            }
        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ForgotPassword.class));
            }
        });

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                
                if (email.length()==0 || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter email id or Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.hide();
                        if (task.isSuccessful()){
                            if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                            }else {
                                Toast.makeText(MainActivity.this,"Please Verify your email id", Toast.LENGTH_SHORT).show();

                            }

                        }else {
                            Toast.makeText(MainActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });




    };
}