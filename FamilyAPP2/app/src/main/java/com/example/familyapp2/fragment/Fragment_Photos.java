package com.example.familyapp2.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.familyapp2.ImagesActivity;
import com.example.familyapp2.R;
import com.example.familyapp2.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Fragment_Photos extends Fragment_Uploads {

    private EditText filename;
    private ProgressBar mProgressBar;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        Button btnChooseImage = view.findViewById(R.id.btn_choose_photo);
        Button btnUpload = view.findViewById(R.id.btn_upload_photo);
        TextView showUploads = view.findViewById(R.id.tv_upload);
        filename = view.findViewById(R.id.et_photo_name);
        setImageView((ImageView)view.findViewById(R.id.iv_photo));
        mProgressBar = view.findViewById(R.id.progress);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        mStorageRef = FirebaseStorage.getInstance().getReference(currentUser.getUid());
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts/" + currentUser.getUid());

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
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
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            + "." + getFileExtension(mImageUri));

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

                    Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_LONG).show();
                    /*
                    Upload upload = new Upload(filename.getText().toString().trim(),
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                    String uploadID = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadID).setValue(upload);*/
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Upload upload = new Upload(filename.getText().toString().trim(),
                                    uri.toString());
                            String uploadID = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadID).setValue(upload);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
            //getImageView().setImageURI(getFileUri());
            Picasso.get().load(getFileUri()).into(getImageView());
        }
    }
}