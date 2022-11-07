package com.example.th10_linh;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button register,signIn;
    private EditText mail, password;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=(Button) findViewById(R.id.buttonLSu);
        register.setOnClickListener(this);

        signIn=(Button) findViewById(R.id.buttonLg);
        signIn.setOnClickListener(this);

        mail=(EditText) findViewById(R.id.mailLogin);
        password=(EditText) findViewById(R.id.passLogin);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        mAuth=FirebaseAuth.getInstance();

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonLSu:
                startActivity(new Intent(this,SignUp.class));
                break;

            case R.id.buttonLg:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email=mail.getText().toString().trim();
        String pass=password.getText().toString().trim();

        if (email.isEmpty()){
            mail.setError("Email is required!");
            mail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mail.setError("Please enter a valid email!");
            mail.requestFocus();
            return;
        }

        if (pass.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if (password.length()<6){
            password.setError("Min password length is 6 characters!");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,list.class));
                }else{
                    Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}