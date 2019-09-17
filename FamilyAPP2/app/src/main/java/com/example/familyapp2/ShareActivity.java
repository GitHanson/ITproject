package com.example.familyapp2;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.familyapp2.fragment.Fragment_Documents;
import com.example.familyapp2.fragment.Fragment_Photos;
import com.example.familyapp2.fragment.Fragment_Videos;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

public class ShareActivity extends AppCompatActivity {

    public static final int VERIFY_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        if(checkPermissionsArray(Permissions.PERMISSIONS)) {
            TabLayout tabLayout = findViewById(R.id.tabLayout);
            ViewPager viewPager = findViewById(R.id.viewPager);

            ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());
            //Add fragments
            adapter.addFragment(new Fragment_Photos(), "Photos");
            adapter.addFragment(new Fragment_Videos(), "Videos");
            adapter.addFragment(new Fragment_Documents(), "Documents");
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }
        else {
            verifyPermissions(Permissions.PERMISSIONS);
        }
    }

    public boolean checkPermissionsArray(String[] permissions) {
        for (String check:permissions) {
            if(!checkPermissions(check)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPermissions(String permission) {
        int permissionRequest = ActivityCompat.checkSelfPermission(ShareActivity.this,
                permission);
        return (permissionRequest != PackageManager.PERMISSION_DENIED);
    }

    public void verifyPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(ShareActivity.this, permissions,
                VERIFY_PERMISSIONS_REQUEST);
    }
}
