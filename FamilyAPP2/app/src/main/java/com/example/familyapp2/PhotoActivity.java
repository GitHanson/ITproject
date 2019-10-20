package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvDescription;
    private ImageButton delete;
    private ImageButton back;
    private ImageButton edit;

    private String description;
    private String imageUrl;
    private String thisKey;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mArtifactRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");

        imageView = findViewById(R.id.imageView);
        tvDescription = findViewById(R.id.description);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        description = extras.getString("DESCRIPTION");
        imageUrl = extras.getString("ARTIFACT_URL");
        thisKey = extras.getString("theKey");

        mArtifactRef = mDatabaseRef.child(thisKey);

        mArtifactRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Artifacts artifacts = dataSnapshot.getValue(Artifacts.class);
                Picasso.get()
                        .load(artifacts.getArtifactUrl())
                        .placeholder(R.mipmap.ic_launcher)
                        //.fit()
                        //.centerCrop()
                        .into(imageView);
                tvDescription.setText(artifacts.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
/*
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                //.fit()
                //.centerCrop()
                .into(imageView);

        //set the description for this artifact
        tvDescription = findViewById(R.id.description);
        tvDescription.setText(description);
*/
        // implement the delete functionality to delet this artifact (photo)
        delete = findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                StorageReference imageRef = mStorage.getReferenceFromUrl(imageUrl);
                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid){
                        mDatabaseRef.child(thisKey).removeValue();
                        Toast.makeText(PhotoActivity.this, "Artifact deleted", Toast.LENGTH_SHORT).show();
                        PhotoActivity.this.finish();
                    }
                });
            }
        });

        //click edit pass to edit artifact details page
        edit = findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PhotoActivity.this, editArtifactDetailActivity.class);
                i.putExtra("KEY", thisKey);
                startActivity(i);
            }
        });

        //implement the go back functionality in this page, to go back previous page
        back = findViewById(R.id.goback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    // override the onBackPressed() method
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
