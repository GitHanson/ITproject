package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;

import com.example.familyapp2.fragment.MeFragment;
import com.google.firebase.auth.FirebaseAuth;

import android.app.FragmentManager;


public class SettingActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //illustrate the layout
        setContentView(R.layout.personal_setting);

        //implement the go back button in setting page
        ImageButton goback = findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingActivity.this,HomeActivity.class);
                startActivity(i);
                /*onBackPressed();*/
            }
        });

        //implement the log out button in setting page
        Button logout = findViewById(R.id.Logout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        //implement the about us button in setting page
        Button aboutUs = findViewById(R.id.AboutUs);
        aboutUs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(i);
            }
        });
        //implement the family code button in setting page
        Button familyCode = findViewById(R.id.FamilyCode);
        familyCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(SettingActivity.this, MyFamilyCodeActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    public void onBackPressed(){


        super.onBackPressed();

    }

}
