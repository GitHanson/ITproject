package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //private EditText userName, emailID, password, confirmation;
    private EditText emailID, password, confirmation;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseUsers;
    private final String defaultProfileUrl = "https://firebasestorage.googleapis.com/v0/b/familyapp-ba107.appspot.com/o/Icon%2Fdefault_profile_icon.png?alt=media&token=7d8a6276-9a3b-4a3d-b87b-231040e9f51d";
    private final String defaultName = "No name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        //userName = findViewById(R.id.editTextUsername);
        emailID = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        confirmation = findViewById(R.id.editTextConfirm);

        ImageButton registerBtn = findViewById(R.id.registerButton);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        //final String name = userName.getText().toString();
        final String email = emailID.getText().toString();
        final String pwd = password.getText().toString();
        String confirm = confirmation.getText().toString();
//        if(name.isEmpty()) {
//            userName.setError("Please enter a username");
//            userName.requestFocus();
//        }
        if(email.isEmpty()) {
            emailID.setError("Please enter your email");
            emailID.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailID.setError("Please enter a valid email");
            emailID.requestFocus();
        }
        else if(pwd.isEmpty()) {
            password.setError("Please enter your password");
            password.requestFocus();
        }
        else if(pwd.length() < 8) {
            password.setError("Password must be at least 8 characters");
            password.requestFocus();
        }
        else if(!pwd.equals(confirm)) {
            confirmation.setError("Passwords must match");
            confirmation.requestFocus();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()) {
                        //Toast.makeText(RegisterActivity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                        Toast.makeText(RegisterActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String userID = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDB = databaseUsers.child(userID);
                        currentUserDB.child("profileUrl").setValue(defaultProfileUrl);
                        currentUserDB.child("name").setValue(defaultName);
                        currentUserDB.child("email").setValue(email);


                        Toast.makeText(RegisterActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }
            });
        }
    }
}
