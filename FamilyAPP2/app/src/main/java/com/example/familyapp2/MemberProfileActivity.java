package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MemberProfileActivity extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private ImageButton goBack;
    private ImageView icon;
    DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members_profile);

        Bundle extras = getIntent().getExtras();
        final String userId = extras.getString("userId");

        name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        goBack = findViewById(R.id.goBack);
        icon = findViewById(R.id.my_profile_photo);


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(SettingActivity.this,HomeActivity.class);
//                startActivity(i);
                onBackPressed();
            }
        });

        // display the icon, name and email
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profileUrl = dataSnapshot.child(userId).child("profileUrl").getValue(String.class);
                String userName = dataSnapshot.child(userId).child("name").getValue(String.class);
                String userEmail = dataSnapshot.child(userId).child("email").getValue(String.class);

                if (profileUrl != null) {
                    Picasso.get().load(profileUrl).into(icon);
                }
                if (userName != null) {
                    name.setText(userName);
                }
                if (userEmail != null) {
                    email.setText(userEmail);
                }

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
