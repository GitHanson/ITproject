package com.example.familyapp2;

import android.content.Intent;
import android.os.Bundle;

import com.example.familyapp2.fragment.HomeFragment;
import com.example.familyapp2.fragment.CategoryFragment;
import com.example.familyapp2.fragment.MeFragment;
import com.example.familyapp2.fragment.TreeFragment;
import com.example.familyapp2.fragment.UploadFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity {
    //private TextView mTextMessage;
    private FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(new HomeFragment(), true);
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_tree:
                    changeFragment(new TreeFragment(), true);
                    //mTextMessage.setText(R.string.title_tree);
                    return true;
                case R.id.navigation_upload:
                    //changeFragment(new UploadFragment(), true);
                    //mTextMessage.setText(R.string.title_upload);
                    Intent upload = new Intent(HomeActivity.this, ShareActivity.class);
                    startActivity(upload);
                    break;
                case R.id.navigation_category:
                    changeFragment(new CategoryFragment(), true);
                    //mTextMessage.setText(R.string.title_like);
                    return true;
                case R.id.navigation_me:
                    changeFragment(new MeFragment(), true);
                    //mTextMessage.setText(R.string.title_me);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //initial fragmentManager
        fragmentManager = getSupportFragmentManager();
        //change fragment
        changeFragment(new HomeFragment(), false);

    }

    // transfer between fragments
    public void changeFragment(Fragment fragment, boolean isInit) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        if (!isInit) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}
