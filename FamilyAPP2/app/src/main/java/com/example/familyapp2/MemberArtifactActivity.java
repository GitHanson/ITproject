package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familyapp2.fragment.Fragment_Documents;
import com.example.familyapp2.fragment.Fragment_Photos;
import com.example.familyapp2.fragment.Fragment_Videos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MemberArtifactActivity extends AppCompatActivity implements  newImageAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRefArtifact;
    private List<Artifacts> mArtifacts;
    private newImageAdapter mAdapter;
    private ValueEventListener mDBListener;
    private DatabaseReference mDatabaseRef;
    String uid;
    String currUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_artifact);

        Bundle extras = getIntent().getExtras();
        uid = extras.getString("memberUserId");
        currUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //set title
        final TextView formatNameTextView = findViewById(R.id.typename);
        final String formatName = MemberProfileActivity.getFormatName();
        formatNameTextView.setText(formatName);

        // back button
        ImageButton backBtn = findViewById(R.id.goback);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //recycler view
        mRecyclerView = findViewById(R.id.personal_artifact_recycler);
        mRecyclerView.setHasFixedSize(true);

        //set layout as Linear
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mArtifacts = new ArrayList<>();
        mAdapter = new newImageAdapter(MemberArtifactActivity.this, mArtifacts);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(MemberArtifactActivity.this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRefArtifact = mFirebaseDatabase.getReference("Artifacts");
        mDBListener = mRefArtifact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mArtifacts.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Artifacts artifacts = postSnapshot.getValue(Artifacts.class);
                    if(artifacts.getUserId()!= null && artifacts.getUserId().equals(uid)&& artifacts.getFormat().equals(formatName.toLowerCase())&& artifacts.getPrivacy().equals("1")){
                        mArtifacts.add(artifacts);
                        //set Key use for artifact deletion
                        artifacts.setKey(postSnapshot.getKey());
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MemberArtifactActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Artifacts selectedItem = mArtifacts.get(position);
        String artifactUrl = selectedItem.getArtifactUrl();
        String description =  selectedItem.getDescription();
        String format = selectedItem.getFormat();
        String selectedKey = selectedItem.getKey();

        Bundle extras = new Bundle();
        extras.putString("ARTIFACT_URL", artifactUrl);
        extras.putString("DESCRIPTION", description);
        extras.putString("theKey", selectedKey);
        Intent intent = new Intent();

        if (uid.equals(currUserId)) {
            switch(format){
                case Fragment_Photos.FORMAT:
                    intent.setClass(MemberArtifactActivity.this, PhotoActivity.class);
                    break;
                case Fragment_Videos.FORMAT:
                    intent.setClass(MemberArtifactActivity.this, VideoActivity.class);
                    break;
                case Fragment_Documents.FORMAT:
                    intent.setClass(MemberArtifactActivity.this, DocumentActivity.class);
            }
            intent.putExtras(extras);
            startActivity(intent);
        } else {
            switch (format) {
                case Fragment_Photos.FORMAT:
                    intent.setClass(MemberArtifactActivity.this, memberPhotoActivity.class);
                    break;
                case Fragment_Videos.FORMAT:
                    intent.setClass(MemberArtifactActivity.this, memberVideoActivity.class);
                    break;
                case Fragment_Documents.FORMAT:
                    intent.setClass(MemberArtifactActivity.this, memberDocumentActivity.class);
            }
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    protected void onDestory(){
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}
