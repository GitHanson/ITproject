package com.example.familyapp2.fragment;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.familyapp2.PersonalArtifactActivity;
import com.example.familyapp2.R;
import com.example.familyapp2.SettingActivity;


public class MeFragment extends Fragment {
    public static String typeName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_me, null);

        //implement the setting button in profile page
        ImageButton setting =  v.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getActivity(), SettingActivity.class);
                startActivity(i);
            }
        });

        //implement myphoto button in profile page
        final ImageButton myphoto = v.findViewById(R.id.myphoto);
        myphoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setTypeName("My Photo");
                Intent i = new Intent(getActivity(), PersonalArtifactActivity.class);
                startActivity(i);
            }
        });

        //implemet myvideo button in profile page
        ImageButton myvideo = v.findViewById(R.id.myvideo);
        myvideo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setTypeName("My Video");
                Intent i = new Intent(getActivity(), PersonalArtifactActivity.class);
                startActivity(i);
            }
        });

        //implemet mydocument button in profile page
        ImageButton mydocument = v.findViewById(R.id.mydocument);
        mydocument.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setTypeName("My Document");
                Intent i = new Intent(getActivity(), PersonalArtifactActivity.class);
                startActivity(i);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
    public static String getTypeName(){
        return typeName;
    }
    private void setTypeName(String typeName){
        this.typeName = typeName;
    }
}
