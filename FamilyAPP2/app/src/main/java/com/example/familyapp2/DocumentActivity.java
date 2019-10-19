package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;



public class DocumentActivity extends AppCompatActivity {


    private ImageView pdfView;
    private ImageButton goback;
    private ImageButton edit;
    private ImageButton delete;

    private TextView tvDescription;
    private String description;
    private String documentUrl;
    private String thumbnailUrl;
    private String thisKey;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;

    private WebView webView;

   
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Artifacts");


        webView = findViewById(R.id.pdfView);
        tvDescription = findViewById(R.id.description);


        String googleDocs = "https://docs.google.com/viewer?url=";

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        description = extras.getString("DESCRIPTION");
        documentUrl = extras.getString("ARTIFACT_URL");
        thisKey = extras.getString("theKey");

        mDialog = new ProgressDialog(DocumentActivity.this);
        mDialog.setMessage("Document loading. Please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        webView.getSettings().setJavaScriptEnabled(true);
        try {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    mDialog.dismiss();
                }
            });
            webView.loadUrl(googleDocs + URLEncoder.encode(documentUrl, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(DocumentActivity.this, "An error has occurred.", Toast.LENGTH_SHORT).show();
        }

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
                i.putExtra("KEY", thisKey);
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
