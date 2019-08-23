package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerBtn = findViewById(R.id.button);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                android.content.Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
