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


public class DocumentActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_document);
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

        //click edit pass to edit artifact details page
        edit = findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DocumentActivity.this, editArtifactDetailActivity.class);
                startActivity(i);
            }
        });

        // delete button to delete this artifact(document)
        delete = findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                StorageReference imageRef = mStorage.getReferenceFromUrl(documentUrl);
                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid){
                        mDatabaseRef.child(thisKey).removeValue();
                        Toast.makeText(DocumentActivity.this, "Artifact deleted", Toast.LENGTH_SHORT).show();
                        DocumentActivity.this.finish();
                    }
                });
            }
        });




    }

    //override the onBackPressed() method
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
