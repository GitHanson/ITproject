package com.example.familyapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class newImageAdapter extends RecyclerView.Adapter<newImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Artifacts> mArtifacts;
    private OnItemClickListener mListener;

    public newImageAdapter(Context context, List<Artifacts> artifacts) {
        mContext = context;
        mArtifacts = artifacts;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_artifact, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Artifacts artifactsCurrent = mArtifacts.get(position);
        Picasso.get()
                .load(artifactsCurrent.getThumbnailUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
        String artifactsDescription = artifactsCurrent.getDescription();
        holder.textView.setText(artifactsDescription);
    }

    @Override
    public int getItemCount() {
        return mArtifacts.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView textView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.profile_image);
            imageView.setOnClickListener(this);

            textView = itemView.findViewById(R.id.profile_description);
            //textView.setOnClickListener(this);

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
