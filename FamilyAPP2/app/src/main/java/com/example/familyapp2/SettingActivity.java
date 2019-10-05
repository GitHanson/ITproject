package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;


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
                Intent i = new Intent(SettingActivity.this, HomeActivity.class);
                startActivity(i);
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
        Button aboutus = findViewById(R.id.AboutUs);
        aboutus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(i);
            }
        });
        //implement the family code button in setting page
        Button familycode = findViewById(R.id.FamilyCode);
        familycode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(SettingActivity.this, MyFamilyCodeActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    public void onBackPressed(){
        /*ImageButton goback = findViewById(R.id.goback);
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if(count == 0){
            super.onBackPressed();
        }else{
            getSupportFragmentManager().popBackStack();
        }*/
        super.onBackPressed();

    }

}
