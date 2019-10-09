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
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familyapp2.fragment.CategoryFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryArtifactActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    String cateName;

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
        mRef = mFirebaseDatabase.getReference("Artifacts");



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

        FirebaseRecyclerOptions<Artifacts> options =
                new FirebaseRecyclerOptions.Builder<Artifacts>()
                        .setQuery(mRef, Artifacts.class)
                        .build();

//        Query query = mRef.orderByChild("family_category_privacy").equalTo("03_"+cateName.toLowerCase()+"_1");
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//
//                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
//                        // do with your result
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        FirebaseRecyclerAdapter<Artifacts, CategoryViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Artifacts, CategoryViewHolder>(options) {

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.list_item_artifact, parent, false);

                        return new CategoryViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull Artifacts model) {
                        //if (model.getFamily_category_privacy().equals("03_"+cateName.toLowerCase()+"_1")) {
                            holder.setDetails(getApplicationContext(), model.getArtifactUrl());
                        //}
                    }

                };

        //set adapter to recycler view
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    // back to previous activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
