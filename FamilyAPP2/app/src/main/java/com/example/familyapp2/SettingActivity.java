package com.example.familyapp2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.net.URI;


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

        //implement the change profile photo page
        Button changePhoto = findViewById(R.id.changeProfilePhoto);
        changePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(SettingActivity.this, ProfilePhotoActivity.class);
                startActivity(i);
            }
        });


    }
    @Override
    public void onBackPressed(){


        super.onBackPressed();

    }
    //protected void onActivityResult(int requestCode, int resultCode,Intent data) {
       // super.onActivityResult();

        /*if(requestCode == Gallery_Request && resultCode == RESULT_OK){
            URI imagePath = data.getData();
            CropImage.activity(imagePath)
                    .setrGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(SettingActivity.this);
                    }
                    */

    //}

}
