package com.example.familyapp2;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.net.Uri;
import android.content.ContentResolver;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfilePhotoActivity extends AppCompatActivity {

    private static final int PHOTO_REQUEST =  1;
    private Uri iconUri;
    FirebaseUser user;
    String uid;
    String newIconUri;

    private Button iconChange;
    private Button iconUpdate;
    private Button iconConfirm;
    private ImageView newIcon;
    private ProgressBar prossBar;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photo);

        newIcon = findViewById(R.id.icon_view);
        prossBar = findViewById(R.id.progressBar);
        // identify the current user and get the user ID
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        storageReference = FirebaseStorage.getInstance().getReference("Icon").child(uid);
        databaseReference = FirebaseDatabase.getInstance().getReference("Icon").child(uid);

        //implement the functionality of accessing the local album and begin choosing picture as icon
        iconChange = findViewById(R.id.icon_change);
        iconChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        // implement the functionality of update icon
        iconUpdate = findViewById(R.id.update_icon);
        iconUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadIcon();
            }
        });
        // implement the functionality of icon confirm and back to setting page
        iconConfirm = findViewById(R.id.back);
        iconConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PHOTO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            iconUri = data.getData();
            Picasso.get().load(iconUri).into(newIcon);
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    //Do the upload functionality to upload the icon to database
    private void uploadIcon(){
        if(iconUri != null){
            final StorageReference iconReference = storageReference.child(System.currentTimeMillis()
            + "." + getFileExtension(iconUri));
            iconReference.putFile(iconUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            prossBar.setProgress(0);
                        }
                    }, 500);

                    Toast.makeText(ProfilePhotoActivity.this, "Profile Photo Update Successful", Toast.LENGTH_LONG).show();

                    // URI of the new icon
                    iconReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // create an new icon and store it in both storage and database
                            Icon icon = new Icon(uri.toString());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(icon);

                            //set my icon to this uploaded photo's uri
                            databaseReference2 = FirebaseDatabase.getInstance().getReference("Users");
                            databaseReference2.child(uid).child("profileUrl").setValue(uri.toString());
                        }
                    });
                }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure (@NonNull Exception e){
                        Toast.makeText(ProfilePhotoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        prossBar.setProgress((int) progress);
                    }
                });
        }
        else{
            Toast.makeText(this, "No icon selected", Toast.LENGTH_SHORT).show();
            }

    }
}
