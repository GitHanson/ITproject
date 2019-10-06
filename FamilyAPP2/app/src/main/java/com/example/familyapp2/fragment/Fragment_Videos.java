package com.example.familyapp2.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.familyapp2.ImagesActivity;
import com.example.familyapp2.R;
import com.example.familyapp2.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Fragment_Videos extends Fragment_Uploads {

    public static final String FORMAT = "video";
    public static final String THUMBNAIL_EXTENSION = "jpg";

    private EditText description;
    private ProgressBar mProgressBar;

    private StorageReference mStorageRef;
    private StorageReference thumbnailRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    private Bitmap thumbnail;
    private String thumbnailDownloadUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        setImageButton((ImageButton) view.findViewById(R.id.uploadButton));
        Button btnUpload = view.findViewById(R.id.btn_upload_video);
        description = view.findViewById(R.id.et_description);
        mProgressBar = view.findViewById(R.id.progress);

        mStorageRef = FirebaseStorage.getInstance().getReference("Artifacts");
        thumbnailRef = FirebaseStorage.getInstance().getReference("Thumbnails");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");

        getImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser(VIDEO_FORMAT);
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

        return view;
    }

    public void uploadFile() {
        Uri mVideoUri = getFileUri();
        if(mVideoUri != null) {
            long currentTime = System.currentTimeMillis();
            final StorageReference fileReference = mStorageRef.child(currentTime
                    + "." + getFileExtension(mVideoUri));
            final StorageReference thumbnailReference = thumbnailRef.child(currentTime
                    + "." + THUMBNAIL_EXTENSION);
            mUploadTask = fileReference.putFile(mVideoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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

                    //Store Thumbnail
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    final byte[] data = baos.toByteArray();
                    thumbnailReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            thumbnailDownloadUrl = uri.toString();
                            UploadTask uploadTask = thumbnailReference.putBytes(data);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    /*
                    UploadTask uploadTask = thumbnailReference.putBytes(data);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });*/

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Upload upload = new Upload(description.getText().toString().trim(),
                                    uri.toString(), thumbnailDownloadUrl, FORMAT);
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
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ARTIFACT_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            setFileUri(data.getData());
            Glide.with(getContext())
                    .asBitmap()
                    .load(getFileUri())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            getImageButton().setImageBitmap(resource);
                            thumbnail = resource;
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }
}
