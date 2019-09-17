package com.example.familyapp2.fragment;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.familyapp2.Permissions;
import com.example.familyapp2.R;
import com.example.familyapp2.ShareActivity;

public class Fragment_Photos extends Fragment {

    public static final int CAMERA_REQUEST_CODE = 16;

    private GridView gridView;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Button btnTakePhoto, btnGallery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos,container,false);
        //gridView = view.findViewById(R.id.gridView);
        //imageView = view.findViewById(R.id.galleryImageView);
        //btnTakePhoto = view.findViewById(R.id.take_photo);
        //btnGallery = view.findViewById(R.id.gallery);
        //progressBar = view.findViewById(R.id.progressbar);
        //progressBar.setVisibility(View.GONE);
/*
        ImageView close = view.findViewById(R.id.ivClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        TextView next = view.findViewById(R.id.tvNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((ShareActivity)getActivity()).checkPermissions(Permissions.PERMISSIONS[2])) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,CAMERA_REQUEST_CODE);
                }
                else {
                    Intent intent = new Intent(getActivity(), ShareActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
*/
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE) {
            //navigate to final share screen to publish photo
        }
    }
}
