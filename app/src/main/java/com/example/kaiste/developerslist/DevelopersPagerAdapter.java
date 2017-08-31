package com.example.kaiste.developerslist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Anyaso Chibugo Franklin 7/14/2017
 */

public class DevelopersPagerAdapter extends FragmentPagerAdapter{
    //Array of tab names
    private String TabTitle[] = new String[]{"Java","PHP","Javascript","Python"};

    /**
     * constructor method
     * @param fm
     */
    public DevelopersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //display the list of developers for the selected tab
       if(position == 0){
           return new JavaFragment();
       }else if(position == 1){
           return new PhpFragment();
       }else if(position == 2){
           return new JavaScriptFragment();
       }else{
           return null;
       }
    }

    /**
     * Total number of tabs to create
     * @return
     */
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TabTitle[position];
    }
}
