package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadArtifactActivity extends AppCompatActivity {

    private Button selectFile, upload;
    private TextView notification;
    private Uri pdfUri;
    private ProgressBar bar;

    private StorageReference mStorageRef;
    private DatabaseReference databaseArtifacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_artifact);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        selectFile = findViewById(R.id.select);
        upload = findViewById(R.id.upload);
        notification = findViewById(R.id.textView);
        bar = findViewById(R.id.progressbar);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(UploadArtifactActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPDF();
                }
                else {
                    ActivityCompat.requestPermissions(UploadArtifactActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdfUri != null) {
                    uploadFile(pdfUri);
                }
                else {
                    Toast.makeText(UploadArtifactActivity.this, "Please select a file", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadFile(Uri pdfUri) {
        bar.setVisibility(View.VISIBLE);
        final String filename = System.currentTimeMillis()+"";

        mStorageRef.child("Uploads").child(filename).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                databaseArtifacts = FirebaseDatabase.getInstance().getReference().child("Artifacts");
                databaseArtifacts.child(filename).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(UploadArtifactActivity.this, "File successfully uploaded", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(UploadArtifactActivity.this, "File upload not successful", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                bar.setVisibility(View.GONE);
                notification.setText("No file selected");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadArtifactActivity.this, "File upload not successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                notification.setText((int) progress + "% Uploading...");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPDF();
        }
        else {
            Toast.makeText(UploadArtifactActivity.this, "Please provide permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Check whether file has been selected
        if(requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            notification.setText("A file is selected: " + data.getData().getLastPathSegment());
        }
        else {
            Toast.makeText(UploadArtifactActivity.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }
}
