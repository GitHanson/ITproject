package com.example.familyapp2;


import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.example.familyapp2.fragment.MeFragment;

public class editProfileDetailActivity extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_edit);

        prefs = getSharedPreferences("MY_DATA", MODE_PRIVATE);
        String Username = prefs.getString("MY_NAME", "");
        String Useremail = prefs.getString("MY_EMAIL", "");

        EditText name = (EditText) findViewById(R.id.Name);
        EditText email = (EditText) findViewById(R.id.Email);

        // Set default value.
        name.setText(Username);
        email.setText(Useremail);

    }
    public void saveData(View view) {
        // Get input text.
        String Username = name.getText().toString();
        String Useremail = email.getText().toString();

        // Save data.
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("MY_NAME", Username);
        editor.putString("MY_EMAIL", Useremail);
        editor.apply();

        // Return to main activity.
        startActivity(new Intent(getApplicationContext(), MeFragment.class));
        //onBackPressed();
    }

    @Override
    public void onBackPressed(){
        /*ImageButton goback = findViewById(R.id.goback);
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if(count == 0){
            super.onBackPressed();
        }else{
            getSupportFragmentManager().popBackStack();
        }*/
        super.onBackPressed();

    }


}



