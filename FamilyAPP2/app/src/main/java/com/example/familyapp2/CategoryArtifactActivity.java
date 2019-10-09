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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryArtifactActivity extends AppCompatActivity {

//    FirebaseDatabase database;
//    DatabaseReference userRef;
//    DatabaseReference artifactRef;
//
//    private RecyclerView mRecyclerView;
//    private ImageAdapterCategory mAdapter;
//    private List<Artifact> mArtifacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_artifact);

//        mRecyclerView = findViewById(R.id.category_recycler);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        mArtifacts = new ArrayList<>();
//
//        // init firebase
//        database = FirebaseDatabase.getInstance();
//        userRef = database.getReference("Users");
//        artifactRef = database.getReference("Artifacts");
//
//        artifactRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot :  dataSnapshot.getChildren()) {
//                    Artifact artifact = postSnapshot.getValue(Artifact.class);
//                    artifact.setKey(postSnapshot.getKey());
//                    mArtifacts.add(artifact);
//                }
//
//                mAdapter = new ImageAdapterCategory(CategoryArtifactActivity.this, mArtifacts);
//
//                mRecyclerView.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(CategoryArtifactActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });





        // different category name
        TextView cateNameTextView = findViewById(R.id.cateName);
        String cateName = CategoryFragment.getCategoryName();
        cateNameTextView.setText(cateName);

        // implement back button
        ImageButton cateBackBtn = findViewById(R.id.cateBackBtn);
        cateBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //list of artifacts
//        ListView artifactListView = CategoryArtifactActivity.this.findViewById(R.id.cate_artifact_list);
//        artifactListView.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return 5;
//            }  //how many artifact in this category
//
//            @Override
//            public Object getItem(int i) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int i) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                return LayoutInflater.from(CategoryArtifactActivity.this).inflate(R.layout.list_item_artifact, null);
//            }
//        });
    }



    // back to previous activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
