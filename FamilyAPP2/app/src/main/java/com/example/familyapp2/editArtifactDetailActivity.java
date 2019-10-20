package com.example.familyapp2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editArtifactDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageButton back;
    private EditText editDescription;
    private Button saveIt;
    private Spinner categorySpinner;
    private ToggleButton privacySetting;

    private String mKey;
    private String description;
    private String category;
    private String privacy;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_artifact_detail_edit);
        mKey = getIntent().getStringExtra("KEY");

        databaseReference = FirebaseDatabase.getInstance().getReference("Artifacts");

        back = findViewById(R.id.goback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editDescription = findViewById(R.id.newDescription);
        categorySpinner = findViewById(R.id.spinner_categories);
        privacySetting = findViewById(R.id.privacy_toggle);

        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.categories, R.layout.category_spinner_layout);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        privacySetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    // private = 0
                    privacy = "0";
                }
                else {
                    // public = 1
                    privacy = "1";
                }
            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                description = dataSnapshot.child(mKey).child("description").getValue(String.class);
                category = dataSnapshot.child(mKey).child("category").getValue(String.class);
                privacy = dataSnapshot.child(mKey).child("privacy").getValue(String.class);

                editDescription.setText(description);

                int spinnerPosition = spinnerAdapter.getPosition(category);
                categorySpinner.setSelection(spinnerPosition);

                if(privacy.equals("0")) {
                    privacySetting.setChecked(true);
                }
                else if(privacy.equals("1")) {
                    privacySetting.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //implement the save button functionality
        saveIt = findViewById(R.id.save);
        saveIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateArtifact(editDescription.getText().toString().trim(), category, privacy);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void updateArtifact(final String description, final String category, final String privacy) {
        final DatabaseReference artifactReference = databaseReference.child(mKey);
        artifactReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Artifacts artifacts = dataSnapshot.getValue(Artifacts.class);
                if(artifacts != null) {
                    artifacts.setDescription(description);
                    artifacts.setCategory(category);
                    artifacts.setPrivacy(privacy);
                    artifactReference.setValue(artifacts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

