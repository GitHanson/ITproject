package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class memberDocumentActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_members_document);

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

        mDialog = new ProgressDialog(memberDocumentActivity.this);
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
            Toast.makeText(memberDocumentActivity.this, "An error has occurred.", Toast.LENGTH_SHORT).show();
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







    }

    //override the onBackPressed() method
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
