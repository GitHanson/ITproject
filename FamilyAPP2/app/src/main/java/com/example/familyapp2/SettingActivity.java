package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;


public class SettingActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_setting);

        Button logout = findViewById(R.id.Logout);


        Button aboutus = findViewById(R.id.AboutUs);
        aboutus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent i = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(i);
            }
        });

        Button familycode = findViewById(R.id.FamilyCode);

    }

}
