package com.example.familyapp2;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
    }

    //set details to item in recycler view
    public void setDetails(Context ctx, String image_artifact, String image_icon, String userName, String description) {
        //view
        ImageView mImageViewArtifact = mView.findViewById(R.id.home_image);
        ImageView mImageViewIcon = mView.findViewById(R.id.home_icon);
        TextView mTextView = mView.findViewById(R.id.home_description);
        TextView mUserName = mView.findViewById(R.id.home_user_name);
        //set data to views
        Picasso.get().load(image_artifact).into(mImageViewArtifact);
        Picasso.get().load(image_icon).into(mImageViewIcon);
        mTextView.setText(description);
        mUserName.setText(userName);
    }
}
