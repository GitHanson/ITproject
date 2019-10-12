package com.example.familyapp2;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MyFamilyCodeActivity extends AppCompatActivity {
    FirebaseUser user;
    String uid;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfamily_code);

        //implement the go back button in this page
        ImageButton goBack = findViewById(R.id.goback);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyFamilyCodeActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });

        //identifier of the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        final TextView myFamilyCode = findViewById(R.id.code);
        // go in to Users path in database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get value from database
                String userFamilyId = dataSnapshot.child(uid).child("family").getValue(String.class);
                // set the user Family Id
                if(userFamilyId != null){
                    myFamilyCode.setText(userFamilyId);
                }else {
                    myFamilyCode.setText("Please join a family");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        // implement the copy image button, now can copy the text of family code after click this button
        ImageButton copy = findViewById(R.id.copyText);
        copy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                String txt = myFamilyCode.getText().toString();
                System.out.println(txt);
                ClipboardManager clipboardManager = (ClipboardManager)  getSystemService(CLIPBOARD_SERVICE);
                clipboardManager.setText(txt);
            }
        });

    }

}
