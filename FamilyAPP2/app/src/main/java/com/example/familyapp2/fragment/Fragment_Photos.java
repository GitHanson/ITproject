package com.example.familyapp2.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.familyapp2.Artifacts;
import com.example.familyapp2.ImagesActivity;
import com.example.familyapp2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Fragment_Photos extends Fragment_Uploads implements AdapterView.OnItemSelectedListener {

    public static final String FORMAT ="photo";
    private EditText description;
    private ProgressBar mProgressBar;
    private Spinner spinner;
    private ToggleButton toggleButton;
    private ProgressBar uploadSpinner;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference userRef;
    private StorageTask mUploadTask;

    private String privacy;
    private String category;
    private String familyId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        setImageButton((ImageButton) view.findViewById(R.id.uploadButton));
        Button btnUpload = view.findViewById(R.id.btn_upload_photo);
        TextView showUploads = view.findViewById(R.id.tv_upload);
        description = view.findViewById(R.id.et_description);
        mProgressBar = view.findViewById(R.id.progress);
        spinner = view.findViewById(R.id.spinner_categories);
        toggleButton = view.findViewById(R.id.privacy_toggle);
        uploadSpinner = view.findViewById(R.id.uploadSpinner);

        privacy = "1";
        category = "Other";

        mStorageRef = FirebaseStorage.getInstance().getReference("Artifacts");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.categories, R.layout.category_spinner_layout);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    // private = 0
                    privacy = "0";
                }
                else {
                    // public = 1
                    privacy = "1";
                }
            }
        });

        getImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser(IMAGE_FORMAT);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(),"Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }
            }
        });

        showUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ImagesActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void uploadFile() {
        Uri mImageUri = getFileUri();
        if(mImageUri != null) {
            uploadSpinner.setVisibility(View.VISIBLE);
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                + "." + getFileExtension(mImageUri));

            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    familyId = dataSnapshot.child(userId).child("family").getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    }, 500);

                    Toast.makeText(getActivity(), "Artifacts successful", Toast.LENGTH_LONG).show();

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Artifacts artifacts = new Artifacts(description.getText().toString().trim(),
                                    uri.toString(), uri.toString(), FORMAT, category, privacy, userId, familyId);
                            String uploadID = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadID).setValue(artifacts);
                            uploadSpinner.setVisibility(View.GONE);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    uploadSpinner.setVisibility(View.GONE);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            });
        }
        else {
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ARTIFACT_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            setFileUri(data.getData());
            Picasso.get()
                    .load(getFileUri())
                    .into(getImageButton());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
