package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class memberVideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private TextView tvDescription;

    private ImageButton back;


    private String thisKey;
    private Uri videoUri;
    private String videoUrl;
    private MediaController mediaController;

    private String description;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_video);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");


        videoView = findViewById(R.id.videoView);


        //delete = findViewById(R.id.deleteButton);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        description = extras.getString("DESCRIPTION");
        videoUrl = extras.getString("ARTIFACT_URL");
        videoUri = Uri.parse(extras.getString("ARTIFACT_URL"));
        //thisKey = extras.getString("theKey");

        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.setMediaController(new MediaController(memberVideoActivity.this));
        videoView.start();


        //set the description for this artifact
        tvDescription = findViewById(R.id.description);
        tvDescription.setText(description);

        //back button to go back to previous page
        back = findViewById(R.id.goback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    //override the onBackPressed() method
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}
