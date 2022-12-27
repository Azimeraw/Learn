package com.example.learn;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learn.Authontication.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText emailet, passwordet;
    private Button signInbutton;
    private TextView signuptv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = firebaseAuth.getInstance();
        emailet = findViewById(R.id.email);
        passwordet = findViewById(R.id.password);
        signInbutton = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        signuptv = findViewById(R.id.signupTv);
        signInbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Login();
            }
        });

        signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Login(){
        String Email = emailet.getText().toString();
        String Password = passwordet.getText().toString();
        if (TextUtils.isEmpty(Email)) {
            emailet.setError("Enter Your Email");
            return;
        } else if (TextUtils.isEmpty(Password)) {
            passwordet.setError("Enter Your Password");
            return;
        }

        progressDialog.setMessage("Pleas wait");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Succefull Login", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(MainActivity.this, "SignIn Fail", Toast.LENGTH_LONG).show();

                progressDialog.dismiss();
            }

        });

    }

    }
