package com.example.familyapp2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.familyapp2.Artifacts;
import com.example.familyapp2.CategoryArtifactActivity;
import com.example.familyapp2.DocumentActivity;
import com.example.familyapp2.HomeActivity;
import com.example.familyapp2.PersonalArtifactActivity;
import com.example.familyapp2.PhotoActivity;
import com.example.familyapp2.VideoActivity;
import com.example.familyapp2.ViewHolder;
import com.example.familyapp2.R;
//import com.example.familyapp2.homeImageAdapter;
import com.example.familyapp2.memberDocumentActivity;
import com.example.familyapp2.memberVideoActivity;
import com.example.familyapp2.memberPhotoActivity;

import com.example.familyapp2.homeImageAdapter;
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

public class HomeFragment extends Fragment implements homeImageAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRefArtifact;
    private DatabaseReference mRefUser;
    private List<Artifacts> mArtifacts;
    private homeImageAdapter mAdapter;
    private ValueEventListener mDBListener;
    private String familyId;
    private String profileUrl;
    private String userName;
    private FirebaseAuth mAuth;
    private String family_privacyValue;
    private DatabaseReference mDatabaseRef;
    private String uid;
    private DatabaseReference mDatabaseRef1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, null);

        //recycler view
        mRecyclerView = view.findViewById(R.id.home_recycler);
        mRecyclerView.setHasFixedSize(true);

        //set layout as Linear
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mArtifacts = new ArrayList<>();
        mAdapter = new homeImageAdapter(getActivity(), mArtifacts);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        //send query to Firebase
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRefArtifact = mFirebaseDatabase.getReference("Artifacts");
        // mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");
        mDatabaseRef1 = FirebaseDatabase.getInstance().getReference("Users");


        mDatabaseRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String familyId = dataSnapshot.child(uid).child("family").getValue(String.class);
                mDBListener = mRefArtifact.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mArtifacts.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Artifacts artifacts = postSnapshot.getValue(Artifacts.class);
                            if (artifacts.getFamily_privacy() != null && artifacts.getUserId() != null && artifacts.getFamily_privacy().equals(familyId + "_" + "1")) {
                                mArtifacts.add(artifacts);
                                //set Key use for artifact deletion
                                artifacts.setKey(postSnapshot.getKey());
                            }

                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Artifacts selectedItem = mArtifacts.get(position);
        String artifactUrl = selectedItem.getArtifactUrl();
        String description = selectedItem.getDescription();
        String format = selectedItem.getFormat();
        String selectedKey = selectedItem.getKey();
        String artifactId = selectedItem.getUserId();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Bundle extras = new Bundle();
        extras.putString("ARTIFACT_URL", artifactUrl);
        extras.putString("DESCRIPTION", description);
        extras.putString("theKey", selectedKey);
        Intent intent = new Intent();

        switch (format) {
            case Fragment_Photos.FORMAT:
                if (artifactId.equals(uid)) {
                    intent.setClass(getActivity(), PhotoActivity.class);
                } else {
                    intent.setClass(getActivity(), memberPhotoActivity.class);
                }
                break;
            case Fragment_Videos.FORMAT:
                if (artifactId.equals(uid)) {
                    intent.setClass(getActivity(), VideoActivity.class);
                } else {
                    intent.setClass(getActivity(), memberVideoActivity.class);
                }
                break;
            case Fragment_Documents.FORMAT:
                if (artifactId.equals(uid)) {
                    intent.setClass(getActivity(), DocumentActivity.class);
                } else {
                    intent.setClass(getActivity(), memberDocumentActivity.class);
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