package com.example.familyapp2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editArtifactDetailActivity extends AppCompatActivity {
    private ImageButton back;
    private EditText editDescription;
    private Button saveIt;


    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_artifact_detail_edit);
        databaseReference = FirebaseDatabase.getInstance().getReference("Artifacts");

        back = findViewById(R.id.goback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editDescription = findViewById(R.id.newDescription);

        //implement the save button functionality
        saveIt = findViewById(R.id.save);
        saveIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

    }

    //override the on back press button
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    //edit description method to save new changes on description
    public void editDescription(){
        String newDescription = editDescription.getText().toString();
        if(!TextUtils.isEmpty(newDescription)){
            //databaseReference.child(uid).child("yearOfBirth").setValue(birthYear);
            editDescription.setText("");
        }
    }
}

