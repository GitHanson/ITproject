package com.example.familyapp2.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

        // get the user name and show the username in personal profile
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.child(uid).child("name").getValue(String.class);
                name.setText(userName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //get the user email and illustrate the email on the personal profile pages
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            String userEmail= mAuth.getCurrentUser().getEmail();
            email.setText(userEmail);
        }

        //implement edit button
        ImageButton edit = v.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getActivity(), editProfileDetailActivity.class);
                startActivity(i);
            }
        });

        //implement the setting button in profile page
        ImageButton setting =  v.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getActivity(), SettingActivity.class);
                startActivity(i);
            }
        });

        //implement myphoto button in profile page
        final ImageButton myPhoto = v.findViewById(R.id.myphoto);
        myPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setTypeName("My Photo");
                Intent i = new Intent(getActivity(), PersonalArtifactActivity.class);
                startActivity(i);
            }
        });

        //implemet myvideo button in profile page
        ImageButton myVideo = v.findViewById(R.id.myvideo);
        myVideo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setTypeName("My Video");
                Intent i = new Intent(getActivity(), PersonalArtifactActivity.class);
                startActivity(i);
            }
        });

        //implemet mydocument button in profile page
        ImageButton myDocument = v.findViewById(R.id.mydocument);
        myDocument.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setTypeName("My Document");
                Intent i = new Intent(getActivity(), PersonalArtifactActivity.class);
                startActivity(i);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
    public static String getTypeName(){
        return typeName;
    }
    private void setTypeName(String typeName){
        this.typeName = typeName;
    }
}
