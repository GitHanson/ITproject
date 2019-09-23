package com.example.familyapp2.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.familyapp2.ImageAdapter;

public abstract class Fragment_Uploads extends Fragment {

    public static final int ARTIFACT_REQUEST = 1;
    public static final String IMAGE_FORMAT = "image/*";
    public static final String VIDEO_FORMAT = "video/*";
    public static final String DOCUMENT_FORMAT = "application/pdf";

    private Uri mFileUri;
    private ImageView mImageView;

    @Nullable
    @Override
    public abstract View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                      @Nullable Bundle savedInstanceState);

    public abstract void uploadFile();

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void openFileChooser(String format) {
        Intent intent = new Intent();
        switch (format) {
            case IMAGE_FORMAT:
                intent.setType(IMAGE_FORMAT);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                break;
            case VIDEO_FORMAT:
                intent.setType(VIDEO_FORMAT);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                break;
            case DOCUMENT_FORMAT:
                intent.setType(DOCUMENT_FORMAT);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                break;
        }
        startActivityForResult(intent, ARTIFACT_REQUEST);
    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ARTIFACT_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            mFileUri = data.getData();
            mImageView.setImageURI(mFileUri);
        }
    }
*/

    public Uri getFileUri() {
        return mFileUri;
    }

    public void setFileUri(Uri uri) {
        mFileUri = uri;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }
}
