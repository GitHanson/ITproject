package com.example.familyapp2.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familyapp2.HomeActivity;
import com.example.familyapp2.JoinFamilyActivity;
import com.example.familyapp2.MemberProfileActivity;
import com.example.familyapp2.R;
import com.example.familyapp2.User;
import com.example.familyapp2.editProfileDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class TreeFragment extends Fragment {

    private TextView familyNameTextView;
    private ImageView treeIcon1;
    private ImageView treeIcon2;
    private ImageView treeIcon3;
    private ImageView treeIcon4;
    private ImageView treeIcon5;
    private ImageView treeIcon6;
    private ImageView treeIcon7;
    private ImageView treeIcon8;
    private List<ImageView> treeIconList = new ArrayList<>();

    private DatabaseReference familyRef;
    private DatabaseReference userRef;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String uid;

    String mUserId;

    private String familyId;
    private List<String> memberList = new ArrayList<>();
    int numOfMembers;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tree, null);


        // assign values to icon
        treeIcon1 = view.findViewById(R.id.tree_icon1);
        treeIcon2 = view.findViewById(R.id.tree_icon2);
        treeIcon3 = view.findViewById(R.id.tree_icon3);
        treeIcon4 = view.findViewById(R.id.tree_icon4);
        treeIcon5 = view.findViewById(R.id.tree_icon5);
        treeIcon6 = view.findViewById(R.id.tree_icon6);
        treeIcon7 = view.findViewById(R.id.tree_icon7);
        treeIcon8 = view.findViewById(R.id.tree_icon8);


        familyNameTextView = view.findViewById(R.id.tree_title_textView);

        final Button familyBtn = view.findViewById(R.id.tree_page_family_btn);

        // add all the icon to the list
        treeIconList.add(treeIcon1);
        treeIconList.add(treeIcon2);
        treeIconList.add(treeIcon3);
        treeIconList.add(treeIcon4);
        treeIconList.add(treeIcon5);
        treeIconList.add(treeIcon6);
        treeIconList.add(treeIcon7);
        treeIconList.add(treeIcon8);



        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        familyRef = FirebaseDatabase.getInstance().getReference("Families");
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        // display family members' profile icon
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot UserDataSnapshot) {
                familyId = UserDataSnapshot.child(uid).child("family").getValue(String.class);
                if (familyId == null) {

                    // implement the button if user has no family
                    familyBtn.setText("Join or create\nyour family");
                    familyBtn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(), JoinFamilyActivity.class);
                            startActivity(i);
                        }
                    });

                } else {
                    // hide the button if the user has family
                    familyBtn.setText("");

                    familyRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String familyName = dataSnapshot.child(familyId).child("familyName").getValue(String.class);
                            familyNameTextView.setText(familyName);

                            for (DataSnapshot child : dataSnapshot.child(familyId).child("members").getChildren()) {
                                String key = child.getKey();
                                memberList.add(key);
                                numOfMembers = memberList.size();
                            }
                            int i = 0;
                            for (final String userId : memberList) {
                                String icon = UserDataSnapshot.child(userId).child("profileUrl").getValue(String.class);
                                Picasso.get().load(icon).into(treeIconList.get(i));

                                treeIconList.get(i).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Bundle extras = new Bundle();
                                        extras.putString("userId",userId);
                                        Intent i = new Intent(getActivity(), MemberProfileActivity.class);
                                        i.putExtras(extras);
                                        startActivity(i);
                                    }
                                });
                                i = i + 1;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
