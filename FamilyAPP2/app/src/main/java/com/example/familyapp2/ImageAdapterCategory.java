package com.example.familyapp2;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapterCategory extends RecyclerView.Adapter<ImageAdapterCategory.ImageViewHolder> {
    private Context mContext;
    private List<Artifact> mArtifacts;

    public ImageAdapterCategory(Context Context, List<Artifact> Artifacts) {
        this.mContext = Context;
        this.mArtifacts = Artifacts;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_artifact, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Artifact artifactCurrent = mArtifacts.get(position);
        Picasso.get()
                .load(artifactCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mArtifacts.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ImageViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.image_artifact);

        }
    }
}
