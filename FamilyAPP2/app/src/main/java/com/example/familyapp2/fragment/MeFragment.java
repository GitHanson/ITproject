package com.example.familyapp2.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.content.SharedPreferences;



import com.example.familyapp2.PersonalArtifactActivity;
import com.example.familyapp2.R;
import com.example.familyapp2.SettingActivity;
import com.example.familyapp2.editProfileDetailActivity;


public class MeFragment extends Fragment {
    public static String typeName;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_me, null);


        TextView name = v.findViewById(R.id.Name);
        TextView email = v.findViewById(R.id.Email);

        SharedPreferences prefs = getContext().getSharedPreferences("MY_DATA",Context.MODE_PRIVATE);
        String Username = prefs.getString("MY_NAME","no name");
        String UserEmail = prefs.getString("MY_EMAIL","no email");

        //set value
        name.setText(Username);
        email.setText(UserEmail);



        //implement edit button
        ImageButton edit = v.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getActivity(), editProfileDetailActivity.class);
                startActivity(i);
            }
        });




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
        final ImageButton myPhoto = v.findViewById(R.id.myphoto);
        myPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setTypeName("My Photo");
                Intent i = new Intent(getActivity(), PersonalArtifactActivity.class);
                startActivity(i);
            }
        });

        //implemet myvideo button in profile page
        ImageButton myVideo = v.findViewById(R.id.myvideo);
        myVideo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setTypeName("My Video");
                Intent i = new Intent(getActivity(), PersonalArtifactActivity.class);
                startActivity(i);
            }
        });

        //implemet mydocument button in profile page
        ImageButton myDocument = v.findViewById(R.id.mydocument);
        myDocument.setOnClickListener(new View.OnClickListener(){
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
