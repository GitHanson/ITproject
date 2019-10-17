package com.example.familyapp2;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class homeImageAdapter extends RecyclerView.Adapter<homeImageAdapter.ImageViewHolder>  {
    private Context mContext;
    private List<Artifacts> mArtifacts;
    private OnItemClickListener mListener;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    //private Object DatabaseReference;

    public homeImageAdapter(Context context, List<Artifacts> artifacts) {
        mContext = context;
        mArtifacts = artifacts;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.home_item, parent, false);
        return new homeImageAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        Artifacts artifactsCurrent = mArtifacts.get(position);
        Picasso.get()
                .load(artifactsCurrent.getThumbnailUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);

        String artifactsDescription = artifactsCurrent.getDescription();
        holder.textView.setText(artifactsDescription);

        final String uid = artifactsCurrent.getUserId();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        //String iconUrl = databaseReference.child(uid).child("profileUrl").toString();
        //Picasso.get().load(iconUrl).into(holder.iconView);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String iconUrl = dataSnapshot.child(uid).child("profileUrl").getValue(String.class);
                Picasso.get().load(iconUrl).into(holder.iconView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        String userId = artifactsCurrent.getUserId();
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get value from database
                String userName = dataSnapshot.child("name").getValue(String.class);
                holder.namView.setText(userName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //String userId = artifactsCurrent.getUserId();
        //String userName = databaseReference.child(userId).child("name").toString();
        //holder.namView.setText(userName);


    }



    @Override
    public int getItemCount() {
        return mArtifacts.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView textView;
        public ImageView iconView;
        public TextView namView;
        //public CircleImageView iconView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.home_image);
            imageView.setOnClickListener(this);

            textView = itemView.findViewById(R.id.home_description);
            //textView.setOnClickListener(this);

            iconView = itemView.findViewById(R.id.home_icon);
            //iconView.setOnClickListener(this);

            namView = itemView.findViewById(R.id.home_user_name);

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

    public void setOnItemClickListener(OnItemClickListener listener) { mListener = listener; }

}

