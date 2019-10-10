package com.example.familyapp2;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
    }

    //set details to item in recycler view
    public void setDetails(Context ctx, String image) {
        //view
        ImageView mImageView = mView.findViewById(R.id.image_artifact);
        //set data to views
        Picasso.get().load(image).into(mImageView);
    }
}
