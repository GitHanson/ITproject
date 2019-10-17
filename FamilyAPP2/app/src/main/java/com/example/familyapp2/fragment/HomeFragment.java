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
import com.example.familyapp2.DocumentActivity;
import com.example.familyapp2.HomeActivity;
import com.example.familyapp2.PersonalArtifactActivity;
import com.example.familyapp2.PhotoActivity;
import com.example.familyapp2.VideoActivity;
import com.example.familyapp2.ViewHolder;
import com.example.familyapp2.R;
import com.example.familyapp2.homeImageAdapter;
import com.example.familyapp2.newImageAdapter;
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

public class HomeFragment extends Fragment{

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


        //mArtifacts = new ArrayList<>();

        //mAdapter = new homeImageAdapter(getActivity(), mArtifacts);
        //mRecyclerView.setAdapter(mAdapter);
        //mAdapter.setOnItemClickListener(HomeFragment.this);

        //send query to Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRefArtifact = mFirebaseDatabase.getReference("Artifacts");
        //mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");


       /* mDBListener = mRefArtifact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mArtifacts.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Artifacts artifacts = postSnapshot.getValue(Artifacts.class);
                    mArtifacts.add(artifacts);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mRefUser = mFirebaseDatabase.getReference("Users");
        mRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                // get family id and family_privacy Value
                familyId = dataSnapshot.child(mAuth.getInstance().getCurrentUser().getUid()).child("family").getValue(String.class);
                family_privacyValue = familyId+"_1";

                // set recycler view
                FirebaseRecyclerOptions<Artifacts> options = new FirebaseRecyclerOptions.Builder<Artifacts>()
                        // add a filter order by family_privacy
                        // privacy 1=visible, 0=invisible
                        .setQuery(mRefArtifact.orderByChild("family_privacy").equalTo(family_privacyValue), Artifacts.class)
                        .build();

                FirebaseRecyclerAdapter<Artifacts, ViewHolder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<Artifacts, ViewHolder>(options) {

                            @NonNull
                            @Override
                            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view_holder = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.home_item, parent, false);
                                return new ViewHolder(view_holder);
                            }

                            @Override
                            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Artifacts model) {
                                String artifactUserId = model.getUserId();
                                String artifactUserName = dataSnapshot.child(artifactUserId).child("name").getValue(String.class);
                                String artifactUserIcon = dataSnapshot.child(artifactUserId).child("profileUrl").getValue(String.class);

                                holder.setDetails(getActivity().getApplicationContext(), model.getThumbnailUrl(), artifactUserIcon, artifactUserName, model.getDescription());
                            }

                        };

                //set adapter to recycler view
                firebaseRecyclerAdapter.startListening();
                mRecyclerView.setAdapter(firebaseRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    /*@Override
    public void onItemClick(int position){
       /* Artifacts selectedItem = mArtifacts.get(position);
        String artifactUrl = selectedItem.getArtifactUrl();
        String description =  selectedItem.getDescription();
        String format = selectedItem.getFormat();
        String selectedKey = selectedItem.getKey();

        Bundle extras = new Bundle();
        extras.putString("ARTIFACT_URL", artifactUrl);
        extras.putString("DESCRIPTION", description);
        extras.putString("theKey", selectedKey);
        Intent intent = new Intent();

        intent.putExtras(extras);
        startActivity(intent);

    }*/
}
