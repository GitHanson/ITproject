package com.example.familyapp2.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.familyapp2.JoinFamilyActivity;
import com.example.familyapp2.R;


public class TreeFragment extends Fragment {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tree, null);

        Button familyBtn = view.findViewById(R.id.tree_page_family_btn);
        familyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), JoinFamilyActivity.class);
                startActivity(i);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
