// after you click a certain category, it jump to this activity
// this contains all the artifact related to this category

package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.familyapp2.fragment.CategoryFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CategoryArtifactActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRefArtifact;
    private DatabaseReference mRefUser;
    private String cateName;
    private String familyId;
    private String profileUrl;
    private String userName;
    private FirebaseAuth mAuth;
    private String family_category_privacyValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_artifact);


        //recycler view
        mRecyclerView = findViewById(R.id.category_recycler);
        mRecyclerView.setHasFixedSize(true);

        //set layout as Linear
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //send query to Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRefArtifact = mFirebaseDatabase.getReference("Artifacts");



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

    }

    //load data into recycler view onStart
    @Override
    protected void onStart() {
        super.onStart();

        mRefUser = mFirebaseDatabase.getReference("Users");
        mRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                //get family id
                familyId = dataSnapshot.child(mAuth.getInstance().getCurrentUser().getUid()).child("family").getValue(String.class);
                family_category_privacyValue = familyId+"_"+cateName+"_1";

                // set recycler view
                FirebaseRecyclerOptions<Artifacts> options = new FirebaseRecyclerOptions.Builder<Artifacts>()
                        // add a filter order by family_category_privacy
                        // privacy 1=visible, 0=invisible
                        .setQuery(mRefArtifact.orderByChild("family_category_privacy").equalTo(family_category_privacyValue), Artifacts.class)
                        .build();

                FirebaseRecyclerAdapter<Artifacts, ViewHolder> firebaseRecyclerAdapter =
                        new FirebaseRecyclerAdapter<Artifacts, ViewHolder>(options) {

                            @NonNull
                            @Override
                            // inflate items in the recycler view
                            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.home_item, parent, false);
                                return new ViewHolder(view);
                            }

                            @Override
                            // set the images
                            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Artifacts model) {
                                String artifactUserId = model.getUserId();
                                String artifactUserName = dataSnapshot.child(artifactUserId).child("name").getValue(String.class);
                                String artifactUserIcon = dataSnapshot.child(artifactUserId).child("profileUrl").getValue(String.class);

                                holder.setDetails(getApplicationContext(), model.getThumbnailUrl(), artifactUserIcon, artifactUserName, model.getDescription());
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

    // back to previous activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
