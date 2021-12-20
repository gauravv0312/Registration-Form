package com.example.studentregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView login_email,login_password,newuser;
    Button btn_login;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        newuser=findViewById(R.id.newuser);
        login_email=findViewById(R.id.login_email);
        login_password=findViewById(R.id.login_password);
        btn_login=findViewById(R.id.btn_login);
        fAuth=FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email =login_email.getText().toString().trim();
                String Password = login_password.getText().toString().trim();
                if (TextUtils.isEmpty(Email))
                {
                    login_email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(Password))
                {
                    login_password.setError("Password is required");
                }
                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "User Successfully logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error!"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }
}
