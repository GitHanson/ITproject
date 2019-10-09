package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private Uri videoUri;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);

        Intent intent = getIntent();
        videoUri = (Uri)intent.getSerializableExtra("VIDEO_URI");

        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                        mediaController = new MediaController(getApplicationContext());
                        videoView.setMediaController(mediaController);
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

        videoView.start();
    }


}
