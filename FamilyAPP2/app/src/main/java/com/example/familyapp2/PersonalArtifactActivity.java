package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.example.familyapp2.fragment.MeFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PersonalArtifactActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRefArtifact;
    private DatabaseReference mRefUser;
    private String userId;
    private String profileUrl;
    private String userName;
    private FirebaseAuth mAuth;
    private String user_format;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_artifact);

        //recycler view
        mRecyclerView = findViewById(R.id.personal_artifact_recycler);
        mRecyclerView.setHasFixedSize(true);

        //set layout as Linear
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //send query to Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRefArtifact = mFirebaseDatabase.getReference("Artifacts");

        // different artifact type name
        TextView typenameTextView = findViewById(R.id.typename);
        String typename = MeFragment.getTypeName();
        typenameTextView.setText("My "+typename);

        //implement back button
        ImageButton goback = findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // get user id
                userId = mAuth.getInstance().getCurrentUser().getUid();
                user_format = userId +"_"+MeFragment.getTypeName().toLowerCase();

                // get profile icon url and user name
                profileUrl = dataSnapshot.child(userId).child("profileUrl").getValue(String.class);
                userName = dataSnapshot.child(userId).child("name").getValue(String.class);

                // set recycler view
                FirebaseRecyclerOptions<Artifacts> options = new FirebaseRecyclerOptions.Builder<Artifacts>()
                        // add a filter order by user_format_privacy
                        // show both private and public
                        .setQuery(mRefArtifact.orderByChild("user_format_privacy").startAt(user_format).endAt(user_format+"\uf8ff"), Artifacts.class)
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
                                holder.setDetails(getApplicationContext(), model.getThumbnailUrl(), profileUrl, userName, model.getDescription());
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


    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
