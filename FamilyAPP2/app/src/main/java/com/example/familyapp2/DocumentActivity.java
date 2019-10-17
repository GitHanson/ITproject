package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DocumentActivity extends AppCompatActivity {

    private WebView webView;
    private TextView tvDescription;
    private String description;
    private String documentUrl;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        webView = findViewById(R.id.pdfView);
        tvDescription = findViewById(R.id.description);

        String googleDocs = "https://docs.google.com/viewer?url=";

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        description = extras.getString("DESCRIPTION");
        documentUrl = extras.getString("ARTIFACT_URL");

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
    }
}
