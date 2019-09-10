package com.example.familyapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.familyapp2.fragment.HomeFragment;
import com.example.familyapp2.fragment.LikeFragment;
import com.example.familyapp2.fragment.MeFragment;
import com.example.familyapp2.fragment.TreeFragment;
import com.example.familyapp2.fragment.UploadFragment;

public class Home2Activity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup group = findViewById(R.id.home_bottom);
    private RadioButton home_home = findViewById(R.id.home_home);
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        //initial fragmentManager
        fragmentManager = getSupportFragmentManager();
        //set default
        home_home.setChecked(true);
        group.setOnCheckedChangeListener(this);
        //change fragment
        changeFragment(new HomeFragment(), false);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedID) {
        switch (checkedID) {
            case R.id.home_home:
                changeFragment(new HomeFragment(), true);
                break;
            case R.id.home_tree:
                changeFragment(new TreeFragment(), true);
                break;
            case R.id.home_upload:
                changeFragment(new UploadFragment(), true);
                break;
            case R.id.home_like:
                changeFragment(new LikeFragment(), true);
                break;
            case R.id.home_me:
                changeFragment(new MeFragment(), true);
                break;

        }
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
