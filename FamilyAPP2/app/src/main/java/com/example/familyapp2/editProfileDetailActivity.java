package com.example.familyapp2;


import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.familyapp2.fragment.MeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editProfileDetailActivity extends AppCompatActivity {
    private EditText name;
    private ImageButton save;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_edit);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();


        name = (EditText) findViewById(R.id.Name);
        save = (ImageButton) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserName();
                Intent i = new Intent(editProfileDetailActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
    public void addUserName(){
        String userName = name.getText().toString();

        if(!TextUtils.isEmpty(userName)){
            databaseReference.child(uid).child("name").setValue(userName);
            name.setText("");

        }
        else{
            Toast.makeText(editProfileDetailActivity.this, "Please type the user name", Toast.LENGTH_SHORT).show();
        }

    }


}



