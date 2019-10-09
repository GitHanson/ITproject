package com.example.familyapp2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.media_image_view, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        //holder.textViewName.setText(uploadCurrent.getName());
        Picasso.get()
                //.load("https://firebasestorage.googleapis.com/v0/b/familyapp-ba107.appspot.com/o/Artifacts%2F1569160347603.jpg?alt=media&token=ee3c657d-7b42-426e-a620-e51712166a1d")
                .load(uploadCurrent.getThumbnailUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
        /*Glide.with(mContext)
                //.load(getUriFromMediaStore(position))
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .centerCrop()
                .override(96,96)
                .into(holder.imageView);*/
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.mediaStoreImageView);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null) {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
