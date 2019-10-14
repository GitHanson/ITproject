package com.example.familyapp2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentList = new ArrayList<>();
    private final ArrayList<String> fragmentListTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitles.get(position);
    }

    /**
     * This function describes the action to add a fragment (with title)
     * to the corresponding fragment list
     * @param fragment the fragment being added to the fragment list
     * @param title the title of fragment list that is being added
     */
    public void addFragment(Fragment fragment, String title) {
        fragmentListTitles.add(title);
        fragmentList.add(fragment);
    }
}
