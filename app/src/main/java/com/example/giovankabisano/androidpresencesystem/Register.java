package com.example.giovankabisano.androidpresencesystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Register extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private ProgressDialog mDialog;
    private Button btnRegister;
    private EditText et_name, et_email, et_password;
    private String email, password, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("UserAPK");
        mDialog = new ProgressDialog(this);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_name = findViewById(R.id.et_name);
                et_email = findViewById(R.id.et_email);
                et_password = findViewById(R.id.et_password);

                name = et_name.getText().toString().trim();
                email = et_email.getText().toString().trim();
                password = et_password.getText().toString().trim();

                registerUser();
            }
        });
    }

    private void registerUser() {
        mDialog.setMessage("Registration Process, Please Wait...");
        mDialog.setIndeterminate(true);
        mDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            OnAuth(task.getResult().getUser());
                            Intent i = new Intent(Register.this, ListOnline.class);
                            startActivity(i);
                            finish();
                        } else{
                            Toast.makeText(Register.this, "Registration Failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void OnAuth(FirebaseUser user) {
        createNewUser(user.getUid());
    }

    private void createNewUser(String uid) {
        UserAPK user = BuildNewUser();
        mDatabase.child(uid).setValue(user);
    }

    private UserAPK BuildNewUser(){
        return new UserAPK(
                name,
                email,
                password,
                new Date().getTime()
        );
    }
}
