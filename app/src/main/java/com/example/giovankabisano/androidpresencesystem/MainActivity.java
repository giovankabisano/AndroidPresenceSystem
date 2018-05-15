package com.example.giovankabisano.androidpresencesystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button buttonLogin,buttonRegister;
    private EditText editTextEmail, editTextPassword;
    private String email, password;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        buttonRegister = findViewById(R.id.btnRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Register.class);
                startActivity(i);
            }
        });

        buttonLogin = findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextEmail = findViewById(R.id.login_emailId);
                editTextPassword = findViewById(R.id.login_passwordId);

                email = editTextEmail.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                userLogin();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!= null){
            updateUI(currentUser);
        }
    }

    //    saat button login diklik, menjalankan ini
    private void userLogin(){
        mDialog.setMessage("Login Please Wait...");
        mDialog.setIndeterminate(true);
        mDialog.show();

        if(email.isEmpty()){
            editTextEmail.setError("Harap Masukan Email");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Tolong Masukan Email Yang Valid");
            editTextEmail.requestFocus();
            return;
        }


        if(password.isEmpty()){
            editTextPassword.setError("Harap Masukan Password");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPassword.setError("Password Harus Memiliki Minimal 6 Karakter");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (task.isSuccessful()){
                    mDialog.dismiss();
                    updateUI(user);
                }else{
                    mDialog.dismiss();
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        Intent i = new Intent(MainActivity.this, ListOnline.class);
        startActivity(i);
    }
}
