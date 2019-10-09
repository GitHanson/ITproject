package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.familyapp2.fragment.Fragment_Documents;
import com.example.familyapp2.fragment.Fragment_Photos;
import com.example.familyapp2.fragment.Fragment_Videos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private FirebaseUser currentUser;
    private ValueEventListener mDBListener;
    private List<Artifacts> mArtifacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        mRecyclerView = findViewById(R.id.image_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mProgressCircle = findViewById(R.id.progress_circle);

        mArtifacts = new ArrayList<>();
        mAdapter = new ImageAdapter(ImagesActivity.this, mArtifacts);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(ImagesActivity.this);

        mAdapter = new ImageAdapter(ImagesActivity.this, mArtifacts);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(ImagesActivity.this);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts/" + currentUser.getUid());
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mArtifacts.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Artifacts artifacts = postSnapshot.getValue(Artifacts.class);
                    //artifacts.setKey(postSnapshot.getKey());
                    mArtifacts.add(artifacts);
                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Artifacts selectedItem = mArtifacts.get(position);
        String artifactUrl = selectedItem.getArtifactUrl();
        String description = selectedItem.getDescription();
        String format = selectedItem.getFormat();

        Bundle extras = new Bundle();
        extras.putString("ARTIFACT_URL", artifactUrl);
        extras.putString("DESCRIPTION", description);
        Intent intent = new Intent();
        switch(format) {
            case Fragment_Photos
                    .FORMAT:
                intent.setClass(ImagesActivity.this, PhotoActivity.class);
                break;
            case Fragment_Videos
                    .FORMAT:
                intent.setClass(ImagesActivity.this, VideoActivity.class);
                break;
            case Fragment_Documents
                    .FORMAT:
                intent.setClass(ImagesActivity.this, DocumentActivity.class);
                break;
        }
        //Intent i = new Intent(ImagesActivity.this, PhotoActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
