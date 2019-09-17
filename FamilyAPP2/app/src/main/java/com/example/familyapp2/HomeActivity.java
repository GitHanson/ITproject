package com.example.familyapp2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    break;
                case R.id.navigation_tree:
                    mTextMessage.setText(R.string.title_tree);
                    break;
                case R.id.navigation_upload:
                    //mTextMessage.setText(R.string.title_upload);
                    Intent upload = new Intent(HomeActivity.this, ShareActivity.class);
                    startActivity(upload);
                    break;
                case R.id.navigation_like:
                    mTextMessage.setText(R.string.title_like);
                    break;
                case R.id.navigation_me:
                    mTextMessage.setText(R.string.title_me);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.upload_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent upload = new Intent(HomeActivity.this, UploadArtifactActivity.class);
                startActivity(upload);
            }
        });
    }

}
