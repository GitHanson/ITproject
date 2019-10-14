package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MemberProfileActivity extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private ImageButton goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members_profile);

        name = (TextView) findViewById(R.id.Name);
        email = (TextView)findViewById(R.id.Email);
        //goBack = findViewById(R.id.goback);
        /*goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(SettingActivity.this,HomeActivity.class);
//                startActivity(i);
                onBackPressed();
            }
        });*/


    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
