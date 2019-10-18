// after you click a certain category, it jump to this activity
// this contains all the artifact related to this category

package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familyapp2.fragment.CategoryFragment;
import com.example.familyapp2.fragment.Fragment_Documents;
import com.example.familyapp2.fragment.Fragment_Photos;
import com.example.familyapp2.fragment.Fragment_Videos;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryArtifactActivity extends AppCompatActivity implements homeImageAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRefArtifact;
    private DatabaseReference mRefUser;
    private String cateName;
    //private String familyId;
    private String profileUrl;
    private String userName;
    private FirebaseAuth mAuth;
    private String family_category_privacyValue;
    //add
    private List<Artifacts> mArtifacts;
    private homeImageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseRef1;
    private ValueEventListener mDBListener;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_artifact);

        // different category name
        TextView cateNameTextView = findViewById(R.id.cateName);
        cateName = CategoryFragment.getCategoryName();
        cateNameTextView.setText(cateName);

        // implement back button
        ImageButton cateBackBtn = findViewById(R.id.cateBackBtn);
        cateBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //recycler view
        mRecyclerView = findViewById(R.id.category_recycler);
        mRecyclerView.setHasFixedSize(true);

        //set layout as Linear
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        /////////////////////////////add artifacts to List
        mArtifacts = new ArrayList<>();
        mAdapter = new homeImageAdapter(CategoryArtifactActivity.this, mArtifacts);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(CategoryArtifactActivity.this);

        //send query to Firebase
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRefArtifact = mFirebaseDatabase.getReference("Artifacts");
       // mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");
        mDatabaseRef1 = FirebaseDatabase.getInstance().getReference("Users");


        //send query to Firebase
       // mFirebaseDatabase = FirebaseDatabase.getInstance();
        //mRefArtifact = mFirebaseDatabase.getReference("Artifacts");
        mDatabaseRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String familyId = dataSnapshot.child(uid).child("family").getValue(String.class);
                mDBListener = mRefArtifact.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mArtifacts.clear();
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Artifacts artifacts = postSnapshot.getValue(Artifacts.class);
                            if(artifacts.getUserId()!= null && artifacts.getFamily_category_privacy()!=null&& artifacts.getFamily_category_privacy().equals(familyId+"_"+cateName+"_"+"1")){
                                mArtifacts.add(artifacts);
                                //set Key use for artifact deletion
                                artifacts.setKey(postSnapshot.getKey());
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(CategoryArtifactActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    // back to previous activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemClick(int position){
        Artifacts selectedItem = mArtifacts.get(position);
        String artifactUrl = selectedItem.getArtifactUrl();
        String description =  selectedItem.getDescription();
        String format = selectedItem.getFormat();
        String selectedKey = selectedItem.getKey();
        String artifactId = selectedItem.getUserId();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        Bundle extras = new Bundle();
        extras.putString("ARTIFACT_URL", artifactUrl);
        extras.putString("DESCRIPTION", description);
        extras.putString("theKey", selectedKey);
        Intent intent = new Intent();

        switch(format){
            case Fragment_Photos.FORMAT:
                if(artifactId.equals(uid)){
                    intent.setClass(CategoryArtifactActivity.this, PhotoActivity.class);
                }else {
                    intent.setClass(CategoryArtifactActivity.this, memberPhotoActivity.class);
                }
                break;
            case Fragment_Videos.FORMAT:
                if(artifactId.equals(uid)){
                    intent.setClass(CategoryArtifactActivity.this, VideoActivity.class);
                }else{
                    intent.setClass(CategoryArtifactActivity.this, memberVideoActivity.class);
                }
                break;
            case Fragment_Documents.FORMAT:
                if(artifactId.equals(uid)){
                    intent.setClass(CategoryArtifactActivity.this, DocumentActivity.class);
                }else {
                    intent.setClass(CategoryArtifactActivity.this, memberDocumentActivity.class);
                }
        }
        intent.putExtras(extras);
        startActivity(intent);
        //Toast.makeText(CategoryArtifactActivity.this, "on click works", Toast.LENGTH_SHORT).show();
    }

    protected void onDestory(){
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

}
