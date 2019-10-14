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


    /**
     * This function describes the action to set the details to the item
     * in the recycler view
     * @param ctx the context
     * @param image_artifact the string source of the artifact in the format of an image
     * @param image_icon the string source of the image icon
     * @param userName the username in the format of a string
     * @param description the description of the artifact in the format of a string
     */
    public void setDetails(Context ctx, String image_artifact, String image_icon,
                           String userName, String description) {
        //view
        ImageView mImageViewArtifact = mView.findViewById(R.id.home_image);
        ImageView mImageViewIcon = mView.findViewById(R.id.home_icon);
        TextView mTextView = mView.findViewById(R.id.home_description);
        TextView mUserName = mView.findViewById(R.id.home_user_name);
        //set data to views
        Picasso.get().load(image_artifact).resize(280,
                180).into(mImageViewArtifact);
        Picasso.get().load(image_icon).into(mImageViewIcon);
        mTextView.setText(description);
        mUserName.setText(userName);
    }


    /**
     *
     * @param ctx the context
     * @param image_artifact the string source of the artifact in the format of an image
     * @param description the description of the artifact in the format of a string
     */
    public void setDetails(Context ctx, String image_artifact, String description) {
        //view
        ImageView mImageViewArtifact = mView.findViewById(R.id.profile_image);
        TextView mTextView = mView.findViewById(R.id.profile_description);
        //set data to views
        Picasso.get().load(image_artifact).resize(280,
                180).into(mImageViewArtifact);
        mTextView.setText(description);
    }

}
