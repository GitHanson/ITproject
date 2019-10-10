package com.example.familyapp2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.familyapp2.Artifacts;
import com.example.familyapp2.ViewHolder;
import com.example.familyapp2.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRefArtifact;
    private DatabaseReference mRefUser;
    private String familyId;
    private String profileUrl;
    private String userName;
    private FirebaseAuth mAuth;
    private String family_privacyValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, null);

        //recycler view
        mRecyclerView = view.findViewById(R.id.home_recycler);
        mRecyclerView.setHasFixedSize(true);

        //set layout as Linear
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //send query to Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRefArtifact = mFirebaseDatabase.getReference("Artifacts");

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mRefUser = mFirebaseDatabase.getReference("Users");
        mRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // get family id and family_privacy Value
                familyId = dataSnapshot.child(mAuth.getInstance().getCurrentUser().getUid()).child("family").getValue(String.class);
                family_privacyValue = familyId+"_1";

                // get profile icon url and user name
                profileUrl = dataSnapshot.child(mAuth.getInstance().getCurrentUser().getUid()).child("profileUrl").getValue(String.class);
                userName = dataSnapshot.child(mAuth.getInstance().getCurrentUser().getUid()).child("name").getValue(String.class);

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
                                holder.setDetails(getActivity().getApplicationContext(), model.getThumbnailUrl(), profileUrl, userName, model.getDescription());
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
}
