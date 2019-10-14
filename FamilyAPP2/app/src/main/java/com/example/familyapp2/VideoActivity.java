package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView tvDescription;
    private Uri videoUri;
    private MediaController mediaController;

    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);
        tvDescription = findViewById(R.id.description);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        description = extras.getString("DESCRIPTION");
        videoUri = Uri.parse(extras.getString("ARTIFACT_URL"));

        videoView.setVideoURI(videoUri);
        videoView.requestFocus();

        videoView.setMediaController(new MediaController(VideoActivity.this));

        videoView.start();

        tvDescription.setText(description);
    }


}
