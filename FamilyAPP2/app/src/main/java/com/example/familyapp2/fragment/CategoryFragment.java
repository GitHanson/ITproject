// this fragment is the category page on main home page

package com.example.familyapp2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.familyapp2.CategoryArtifactActivity;
import com.example.familyapp2.R;

public class CategoryFragment extends Fragment {

    public static String categoryName;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, null);

        //click category buttons
        final Button cateInstru = view.findViewById(R.id.cate_instru);
        cateInstru.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateInstru.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        final Button cateBook = view.findViewById(R.id.cate_book);
        cateBook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateBook.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        final Button cateCloth = view.findViewById(R.id.cate_cloth);
        cateCloth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateCloth.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        final Button catePhoto = view.findViewById(R.id.cate_photo);
        catePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(catePhoto.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        final Button cateHouse = view.findViewById(R.id.cate_house);
        cateHouse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateHouse.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        final Button cateAnti = view.findViewById(R.id.cate_anti);
        cateAnti.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateAnti.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        final Button cateJew = view.findViewById(R.id.cate_jew);
        cateJew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateJew.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        final Button cateOther = view.findViewById(R.id.cate_other);
        cateOther.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateOther.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public static String getCategoryName() {
        return categoryName;
    }

    private void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}


