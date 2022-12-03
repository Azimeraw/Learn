package com.example.learn.Authontication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learn.DashBoardActivity;
import com.example.learn.MainActivity;
import com.example.learn.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;
public class SignUpActivity extends AppCompatActivity {
    private EditText emailet, passwordet1, passwordet2;
    private Button signupbutton;
    private TextView signIntv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = firebaseAuth.getInstance();
        emailet = findViewById(R.id.email);
        passwordet1 = findViewById(R.id.password1);
        passwordet2 = findViewById(R.id.confirm_password);
        signupbutton = findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);
        signIntv = findViewById(R.id.signintv);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Register();
            }
        });

        signIntv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void Register() {
        String Email = emailet.getText().toString();
        String Password = passwordet1.getText().toString();
        String Passwordconfirm = passwordet2.getText().toString();
        if (TextUtils.isEmpty(Email)) {
            emailet.setError("Enter Your Email");
            return;
        } else if (TextUtils.isEmpty(Password)) {
            passwordet1.setError("Enter Your Password");
            return;
        } else if (TextUtils.isEmpty(Passwordconfirm)) {
            passwordet2.setError("Confirem Your Password");
            return;
        } else if (!Password.equals(Passwordconfirm)) {
            passwordet2.setError("Different Password");
            return;
        } else if (Password.length() < 4) {
            passwordet1.setError(" Lengeth should be >4");
            return;
        } else if (!isValidEmail(Email)) {
            emailet.setError("Enter Valid Email");
            return;
        }

        progressDialog.setMessage("Pleas wait");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Succefull registerd", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(SignUpActivity.this, "SignUp Fail", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}

