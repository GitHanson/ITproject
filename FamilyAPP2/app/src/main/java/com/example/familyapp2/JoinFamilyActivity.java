package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.familyapp2.fragment.TreeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JoinFamilyActivity extends AppCompatActivity {

    private EditText invCodeInput;
    private ImageButton joinFamily;
    private ImageButton createFamily;
    private ImageButton backBtn;
    DatabaseReference familyRef;
    DatabaseReference userRef;
    String uid;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_family);

        familyRef = FirebaseDatabase.getInstance().getReference("Families");
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        uid = mAuth.getInstance().getCurrentUser().getUid();

        invCodeInput = findViewById(R.id.editText_join_family_code);
        joinFamily = findViewById(R.id.join_family_btn);
        createFamily = findViewById(R.id.create_family_btn);

        // join family
        joinFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userJoinFamily();
            }
        });
        // create family
        createFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCreateFamily();
            }
        });
        //back button pressed
        backBtn = findViewById(R.id.join_family_BackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    // method for join family
    private void userJoinFamily() {
        final String invCode = invCodeInput.getText().toString();

        // if input not empty
        if(!TextUtils.isEmpty(invCode)){

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot userDataSnapshot) {

                    familyRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot familyDataSnapshot) {
                            // check if user have family already
                            if (userDataSnapshot.child(uid).child("family").getValue() == null) {
                                //check if the family exist
                                if (familyDataSnapshot.getValue().toString().contains(invCode)){
                                    userRef.child(uid).child("family").setValue(invCode);
                                    // add family member
                                    familyRef.child(invCode).child("members").child(uid).setValue(true);
                                    invCodeInput.setText("");
                                    onBackPressed();
                                    Toast.makeText(JoinFamilyActivity.this, "Successfully joined", Toast.LENGTH_SHORT).show();
                                } else {
                                    // family not exist
                                    Toast.makeText(JoinFamilyActivity.this, "The family doesn't exist", Toast.LENGTH_SHORT).show();
                                    invCodeInput.setText("");
                                }

                            } else {
                                // the user already has a family
                                invCodeInput.setText("");
                                Toast.makeText(JoinFamilyActivity.this, "You already have a family, don't join again", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        // error is empty input
        else{
            Toast.makeText(JoinFamilyActivity.this, "Please type the invitation code", Toast.LENGTH_SHORT).show();
        }

    }

    private void userCreateFamily() {

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // check if user has a family already
                // no family
                if (dataSnapshot.child(uid).child("family").getValue() == null) {

                    String familyKey = familyRef.push().getKey();
                    // set family name
                    familyRef.child(familyKey).child("name").setValue("No name");
                    // add family member
                    familyRef.child(familyKey).child("members").child(uid).setValue(true);
                    userRef.child(uid).child("family").setValue(familyKey);
                    onBackPressed();
                    Toast.makeText(JoinFamilyActivity.this, "Successfully created", Toast.LENGTH_SHORT).show();

                } else {
                    // already has family
                    Toast.makeText(JoinFamilyActivity.this, "You already have a family, don't create again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
