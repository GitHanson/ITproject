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

import com.example.familyapp2.CategoryArtifactActivity;
import com.example.familyapp2.R;

public class CategoryFragment extends Fragment {
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, null);

        //click category button
        Button cateInstru = view.findViewById(R.id.cate_instru);
        cateInstru.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryArtifactActivity.class);
                startActivity(i);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
