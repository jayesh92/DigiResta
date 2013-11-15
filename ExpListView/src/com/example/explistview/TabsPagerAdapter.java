package com.example.explistview;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        Log.d("tabpageadapter", Integer.toString(index));
    	switch (index) {
        case 0:
            // Top Rated fragment activity
            Tab1Fragment f = new Tab1Fragment();
            
        	return new Tab1Fragment();
        case 1:
            // Games fragment activity
            return new Tab2Fragment();
        }
        
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
    @Override
    public int getItemPosition(Object object) {
        //if(object instanceof Tab2Fragment)
        	return POSITION_NONE;
        //else
        	//return POSITION_UNCHANGED;
    }  
}