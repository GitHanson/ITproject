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

import com.example.familyapp2.CategoryArtifactActivity;
import com.example.familyapp2.R;

public class CategoryFragment extends Fragment {

    public static String categoryName;

    @NonNull
    @Override
    /**
     * This function describes the actions done when different button is clicked,
     * specifically on the category page
     * @param LayoutInflater inflaterViewGroup container,
     * @param ViewGroup container
     * @param Bundle savedInstanceState
     * @return View It Inflates the layout for this fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, null);


        /*It describes the actions done when different button is clicked*/

        // The Instrument button
        final Button cateInstru = view.findViewById(R.id.cate_instru);
        cateInstru.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateInstru.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        // The Book button
        final Button cateBook = view.findViewById(R.id.cate_book);
        cateBook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateBook.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        // The Cloth button
        final Button cateCloth = view.findViewById(R.id.cate_cloth);
        cateCloth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateCloth.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        // The Photo button
        final Button catePhoto = view.findViewById(R.id.cate_photo);
        catePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(catePhoto.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        // The House button
        final Button cateHouse = view.findViewById(R.id.cate_house);
        cateHouse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateHouse.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        // The Antiques button
        final Button cateAnti = view.findViewById(R.id.cate_anti);
        cateAnti.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateAnti.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        // The Jewelry button
        final Button cateJew = view.findViewById(R.id.cate_jew);
        cateJew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setCategoryName(cateJew.getText().toString());
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });

        // The Other button
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

    /**
     * This function is a getter of the category name
     * @return categoryName The current name of this category
     */
    public static String getCategoryName() {
        return categoryName;
    }

    /**
     * This function is the setter of the name of a category
     * @param categoryName the name of the category
     */
    private void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}


