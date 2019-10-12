package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.example.familyapp2.fragment.MeFragment;



public class PersonalArtifactActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_artifact);

        // different artifact type name
        TextView typenameTextView = findViewById(R.id.typename);
        String typename = MeFragment.getTypeName();
        typenameTextView.setText(typename);

        //implement back button
        ImageButton goback = findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        //list of artifacts
        ListView listitem = PersonalArtifactActivity.this.findViewById(R.id.list_item);


    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();;
    }
}
