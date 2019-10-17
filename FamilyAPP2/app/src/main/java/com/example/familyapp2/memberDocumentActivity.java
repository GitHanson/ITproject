package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class memberDocumentActivity extends AppCompatActivity {
    private ImageView pdfView;
    private ImageView goback;
    private ImageButton edit;
    private ImageButton delete;

    private TextView tvDescription;
    private String description;
    private String documentUrl;
    private String thumbnailUrl;
    private String thisKey;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_document);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");


        pdfView = findViewById(R.id.pdfView);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        description = extras.getString("DESCRIPTION");
        documentUrl = extras.getString("ARTIFACT_URL");
        thumbnailUrl = extras.getString("THUMB");

        Picasso.get()
                .load(thumbnailUrl)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(pdfView);

        pdfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(documentUrl),"application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Intent newIntent = Intent.createChooser(intent, "Open File");
                try {
                    startActivity(newIntent);
                } catch (ActivityNotFoundException e) {

                }
            }
        });

        //set the artifact description
        tvDescription = findViewById(R.id.description);
        tvDescription.setText(description);

        // go back button, can shift to previous page
        goback = findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });





    }

    //override the onBackPressed() method
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
