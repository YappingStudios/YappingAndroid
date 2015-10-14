package com.yAPPING.yappingproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;

    public DemoCollectionPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {

        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // String classNames=  fragments.get(position).getClass().getName();
        String classNames = fragments.get(position).getClass().getName();
        /*switch (classNames) {
            case "FoodFragment":
                return "Food Must Knows";
            //break;
            case "TravelFragment":
                return "Travel Must Knows";
            //break;
            case "LifestyleFragment":
                return "Lifestyle Must Knows";
//            break;
            case "TechFragment":
                return "Tech Must Knows";
//            break;
            case "EducationFragment":
                return "Education Must Knows";
//            break;
        }*/
        if (classNames.equals("FoodFragment")) {

            return "Food Must Knows";
        } else if (classNames.equals("TravelFragment")) {
            return "Travel Must Knows";
        } else if (classNames.equals("LifestyleFragment")) {
            return "Lifestyle Must Knows";
        } else if (classNames.equals("TechFragment")) {
            return "Tech Must Knows";
        } else if (classNames.equals("EducationFragment")) {
            return "Education Must Knows";
        } else {
            return "No Title";
        }
    }
}
