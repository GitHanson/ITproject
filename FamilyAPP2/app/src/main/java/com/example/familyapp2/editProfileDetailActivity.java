package com.example.familyapp2;


import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class editProfileDetailActivity extends AppCompatActivity {
    private EditText name;
    private EditText familyName;
    private ImageButton save;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    FirebaseUser user;
    String uid;
    String ufamilyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_edit);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Families");
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        //ufamilyID = dataSnapshot.child(uid).child("profileUrl").getValue(String.class);;

        // get each button by id
        name = (EditText) findViewById(R.id.Name);
        familyName = (EditText)findViewById(R.id.FamilyName);
        save = (ImageButton) findViewById(R.id.save);

        //implement the save button functionality
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFamilyName();
                addUserName();
                Intent i = new Intent(editProfileDetailActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }

    //store the input text as user name in database
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

    //store the input text as user name in database
    public void addFamilyName(){
        final String userFamilyName = familyName.getText().toString();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get value from database
                 String uFamilyID = dataSnapshot.child(uid).child("family").getValue(String.class);
                if(!TextUtils.isEmpty(userFamilyName)) {
                    databaseReference2.child(uFamilyID).child("familyName").setValue(userFamilyName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}



