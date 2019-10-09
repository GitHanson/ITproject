package com.example.familyapp2.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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

public class Fragment_Documents extends Fragment_Uploads {

    public static final String FORMAT = "document";

    private EditText description;
    private ProgressBar mProgressBar;

    private StorageReference mStorageRef;
    private StorageReference thumbnailRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private StorageTask thumbnailTask;

    private Bitmap thumbnail;
    private String thumbnailDownloadUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_documents, container, false);
        setImageButton((ImageButton) view.findViewById(R.id.uploadButton));
        Button btnUpload = view.findViewById(R.id.btn_upload_document);
        description = view.findViewById(R.id.et_description);
        mProgressBar = view.findViewById(R.id.progress);

        mStorageRef = FirebaseStorage.getInstance().getReference("Artifacts");
        thumbnailRef = FirebaseStorage.getInstance().getReference("Thumbnails");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");

        getImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openPhotoChooser();
                openFileChooser(DOCUMENT_FORMAT);
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

    @Override
    public void uploadFile() {
        Uri mDocumentUri = getFileUri();
        if(mDocumentUri != null) {
            long currentTime = System.currentTimeMillis();
            final StorageReference fileReference = mStorageRef.child(currentTime
                    + "." + getFileExtension(mDocumentUri));
            final StorageReference thumbnailReference = thumbnailRef.child(currentTime
                    + "." + Fragment_Videos.THUMBNAIL_EXTENSION);

            // Adds video thumbnail to storage
            // Store thumbnail into storage
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG,100,baos);
            final byte[] data = baos.toByteArray();
            thumbnailTask = thumbnailReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    thumbnailReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            thumbnailDownloadUrl = uri.toString();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            mUploadTask = fileReference.putFile(mDocumentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
        else {
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ARTIFACT_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            setFileUri(data.getData());
            Glide.with(getContext())
                    .asBitmap()
                    .load(R.drawable.pdf_symbol)
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
