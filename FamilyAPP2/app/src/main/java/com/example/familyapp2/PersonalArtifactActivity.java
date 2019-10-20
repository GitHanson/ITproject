package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import com.example.familyapp2.fragment.MeFragment;
import com.example.familyapp2.fragment.Fragment_Documents;
import com.example.familyapp2.fragment.Fragment_Photos;
import com.example.familyapp2.fragment.Fragment_Videos;
import com.example.familyapp2.ImageAdapter;
import com.example.familyapp2.newImageAdapter;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ArrayList;

//
public class PersonalArtifactActivity extends AppCompatActivity implements newImageAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRefArtifact;
    private DatabaseReference mRefUser;
    private String userId;
    private String profileUrl;
    private String userName;
    private FirebaseAuth mAuth;
    private String user_format;
    private List<Artifacts> mArtifacts;
    //
    private newImageAdapter mAdapter;
    private ValueEventListener mDBListener;
    private DatabaseReference mDatabaseRef;
    String uid;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_artifact);

        // different artifact type name
        final TextView typenameTextView = findViewById(R.id.typename);
        final String typename = MeFragment.getTypeName();
        typenameTextView.setText(typename);

        //implement back button
        ImageButton goback = findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
        mAdapter = new newImageAdapter(PersonalArtifactActivity.this, mArtifacts);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(PersonalArtifactActivity.this);

        //send query to Firebase
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRefArtifact = mFirebaseDatabase.getReference("Artifacts");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");

        mDBListener = mRefArtifact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mArtifacts.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Artifacts artifacts = postSnapshot.getValue(Artifacts.class);
                    if(artifacts.getUserId()!= null && artifacts.getUserId().equals(uid)&& artifacts.getFormat().equals(typename.toLowerCase())){
                        mArtifacts.add(artifacts);
                        //set Key use for artifact deletion
                        artifacts.setKey(postSnapshot.getKey());
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PersonalArtifactActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onItemClick(int position){
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

        switch(format){
            case Fragment_Photos.FORMAT:
                intent.setClass(PersonalArtifactActivity.this, PhotoActivity.class);
                break;
            case Fragment_Videos.FORMAT:
                intent.setClass(PersonalArtifactActivity.this, VideoActivity.class);
                break;
            case Fragment_Documents.FORMAT:
                intent.setClass(PersonalArtifactActivity.this, DocumentActivity.class);
        }
        intent.putExtras(extras);
        startActivity(intent);

    }

    protected void onDestory(){
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
