package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class memberPhotoActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView tvDescription;

    private ImageButton back;


    private String description;
    private String imageUrl;
    private String thisKey;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_photo);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");

        imageView = findViewById(R.id.imageView);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        description = extras.getString("DESCRIPTION");
        imageUrl = extras.getString("ARTIFACT_URL");
        thisKey = extras.getString("theKey");

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageView);

        //set the description for this artifact
        tvDescription = findViewById(R.id.description);
        tvDescription.setText(description);


        //implement the go back functionality in this page, to go back previous page
        back = findViewById(R.id.goback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
