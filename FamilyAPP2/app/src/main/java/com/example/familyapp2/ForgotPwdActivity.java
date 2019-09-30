package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        final EditText userEmail = findViewById(R.id.forgotEmailInput);
        final ProgressBar progressBar = findViewById(R.id.progress);
        ImageButton button = findViewById(R.id.imageButton);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()) {
                                    Toast.makeText(ForgotPwdActivity.this,
                                            "Password reset has been sent to your email.",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ForgotPwdActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(ForgotPwdActivity.this,
                                            task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}
