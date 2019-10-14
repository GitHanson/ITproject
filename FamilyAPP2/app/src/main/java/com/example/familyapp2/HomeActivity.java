package com.example.familyapp2;

import android.content.Intent;
import android.os.Bundle;

import com.example.familyapp2.fragment.HomeFragment;
import com.example.familyapp2.fragment.CategoryFragment;
import com.example.familyapp2.fragment.MeFragment;
import com.example.familyapp2.fragment.TreeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {
    //private TextView mTextMessage;
    private FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /**
         * This function response to the action (by calling the changeFragment method)
         * when an item is selected (the button is clicked and change the fragment
         * into the selected one.
         * @param item the item (totally 5 types: home/tree/upload/category/me) that
         *             indicates different fragments
         * @return a boolean to indicate if the fragment has been successfully changed
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                // The case when the home button pressed
                case R.id.navigation_home:
                    changeFragment(new HomeFragment(), true);
                    return true;

                // The case when the tree button pressed
                case R.id.navigation_tree:
                    changeFragment(new TreeFragment(), true);
                    return true;

                // The case when the upload button pressed
                case R.id.navigation_upload:
                    Intent upload = new Intent(HomeActivity.this, ShareActivity.class);
                    startActivity(upload);
                    break;

                // The case when the category button pressed
                case R.id.navigation_category:
                    changeFragment(new CategoryFragment(), true);
                    return true;

                // The case when the personal profile page pressed
                case R.id.navigation_me:
                    changeFragment(new MeFragment(), true);
                    return true;
            }
            return false;
        }
    };

    /**
     *
     * @param savedInstanceState
     */
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

        //System.out.println(getCallingActivity()+"3333333333333333333");
        //if(this.getCallingActivity().equals(SettingActivity.class)){
         //   changeFragment(new MeFragment(),true);
        //}


    }

    // transfer between fragments

    /**
     * This function changes the fragment by replacing the current fragment with another
     * @param fragment
     * @param isInit
     */
    public void changeFragment(Fragment fragment, boolean isInit) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_container, fragment);
        if (!isInit) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}
