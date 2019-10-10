package com.example.familyapp2.fragment;


import android.content.Intent;
import android.os.Bundle;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familyapp2.PersonalArtifactActivity;
import com.example.familyapp2.R;
import com.example.familyapp2.SettingActivity;
import com.example.familyapp2.editProfileDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.PicassoProvider;


public class MeFragment extends Fragment {
    public static String typeName;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String uid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //use the layout to illustrate this fragment
        View v = inflater.inflate(R.layout.fragment_me, null);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        final TextView name = v.findViewById(R.id.Name);
        final TextView email = v.findViewById(R.id.Email);
        final ImageView profilePhoto = v.findViewById(R.id.my_profile_photo);

        // get the user name and show the username in personal profile
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get value from database
                String userName = dataSnapshot.child(uid).child("name").getValue(String.class);
                String image = dataSnapshot.child(uid).child("profileUrl").getValue(String.class);
                // set the user name
                name.setText(userName);
                // show the profile photo in circle and fit the size
                Picasso.get().load(image).into(profilePhoto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //get the user email and illustrate the email on the personal profile pages
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            String userEmail = mAuth.getCurrentUser().getEmail();
            email.setText(userEmail);
        }

        //implement edit button
        ImageButton edit = v.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), editProfileDetailActivity.class);
                startActivity(i);
            }
        });

        //implement the setting button in profile page
        ImageButton setting = v.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SettingActivity.class);
                startActivity(i);
            }
        });

        //implement myphoto button in profile page
        final ImageButton myPhoto = v.findViewById(R.id.myphoto);
        myPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTypeName("My Photo");
                Intent i = new Intent(getActivity(), PersonalArtifactActivity.class);
                startActivity(i);
            }
        });

        //implemet myvideo button in profile page
        ImageButton myVideo = v.findViewById(R.id.myvideo);
        myVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTypeName("My Video");
                Intent i = new Intent(getActivity(), PersonalArtifactActivity.class);
                startActivity(i);
            }
        });

        //implemet mydocument button in profile page
        ImageButton myDocument = v.findViewById(R.id.mydocument);
        myDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTypeName("My Document");
                Intent i = new Intent(getActivity(), PersonalArtifactActivity.class);
                startActivity(i);
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    public static String getTypeName() {
        return typeName;
    }

    private void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void uploadNewPhoto(String photoType, final String caption, final int count, final String imgUrl) {



    }

}
