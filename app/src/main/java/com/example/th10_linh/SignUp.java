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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText nameSu,passSu,mailSu;
    private Button btSu,btBack;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        AnhXa();
        btBack.setOnClickListener(this);
        btSu.setOnClickListener(this);
        registerUser();


    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.buttonSu:
                registerUser();
                break;
        }
    }
    public void AnhXa(){
        mAuth=FirebaseAuth.getInstance();
        nameSu = (EditText) findViewById(R.id.nameSu);
        passSu = (EditText) findViewById(R.id.passwordSu);
        mailSu = (EditText) findViewById(R.id.mailSu);
        btSu = (Button) findViewById(R.id.buttonSu);
        btBack=(Button) findViewById(R.id.back);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
    }

    private void registerUser(){
        String name=nameSu.getText().toString().trim();
        String pass=passSu.getText().toString().trim();
        String mail=mailSu.getText().toString().trim();
        if (name.isEmpty()){
            nameSu.setError("Full name is required");
            nameSu.requestFocus();
            return;
        }
        if (mail.isEmpty()){
            mailSu.setError("mail is required");
            mailSu.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            mailSu.setError("please provide valid mail");
            mailSu.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            passSu.setError("password is required");
            passSu.requestFocus();
            return;
        }
        if (pass.length()< 6){
            passSu.setError("Min password length should be 6 characters!");
            passSu.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            User user=new User(name,mail);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(SignUp.this, "User has been registered", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }else{
                                                Toast.makeText(SignUp.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        }else{
                            Toast.makeText(SignUp.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}