package com.soldis.yourthaitea.transaction.maintenance.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.soldis.yourthaitea.master.fragment.frag_classification;
import com.soldis.yourthaitea.master.fragment.frag_product;
import com.soldis.yourthaitea.transaction.maintenance.fragment.frag_InputDispenser;
import com.soldis.yourthaitea.transaction.maintenance.fragment.frag_Maintenance;

public class ViewPagerAdapterMaintenance extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapterMaintenance(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            frag_Maintenance tab1 = new frag_Maintenance();
            return tab1;
        }
        else              // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            frag_InputDispenser tab2 = new frag_InputDispenser();
            return tab2;
        }

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}