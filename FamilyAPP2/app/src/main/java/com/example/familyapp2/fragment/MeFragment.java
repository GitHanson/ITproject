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
import android.widget.ImageButton;

import com.example.familyapp2.MyFamilyCodeActivity;
import com.example.familyapp2.R;
import com.example.familyapp2.SettingActivity;


public class MeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_me, null);

        //implement the setting button in setting page
        ImageButton setting =  v.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getActivity(), SettingActivity.class);
                startActivity(i);

            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}
