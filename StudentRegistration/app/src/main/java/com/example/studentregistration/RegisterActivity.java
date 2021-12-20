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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextView fullname,email,number,password,branch,already;
    Button signup;
    FirebaseAuth fAuth;
    String StudentId;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        already=findViewById(R.id.already);
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        number=findViewById(R.id.number);
        password=findViewById(R.id.password);
        branch=findViewById(R.id.branch);
        signup=findViewById(R.id.signup);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        if (fAuth.getCurrentUser() !=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity2.class));
            finish();
        }
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=email.getText().toString().trim();
                String Password=password.getText().toString().trim();
                String FullName=fullname.getText().toString().trim();
                String Number= number.getText().toString().trim();
                String Branch=branch.getText().toString().trim();
                if (TextUtils.isEmpty(Email))
                {
                    email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(Password))
                {
                    password.setError("Password is required");
                }
                if (Password.length()<6)
                {
                    password.setError("password must be greater than 6 ");
                }
                if (TextUtils.isEmpty(FullName))
                {
                    fullname.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(Number))
                {
                    number.setError("Mobile is required");
                    return;
                }
                if (TextUtils.isEmpty(Branch))
                {
                    branch.setError("Branch is required");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "User Successfully inserted", Toast.LENGTH_SHORT).show();
                            StudentId=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Students").document(StudentId);
                            Map<String,Object> student = new HashMap<>();
                            student.put("Student Name",FullName);
                            student.put("Student Number",Number);
                            student.put("Student Email",Email);
                            student.put("Student Branch",Branch);
                            documentReference.set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Student Profile Has Been Created", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}