package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton signupBtn = findViewById(R.id.signupButton);
        signupBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                android.content.Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        ImageButton loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                android.content.Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }

}
