package com.example.explistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
	// more efficient than HashMap for mapping integers to objects
	SparseArray<Group> groups = new SparseArray<Group>();
	Context ctx;
	private ViewPager vp;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionbar;
	MyExpandableListAdapter adapter;
	public String resta;
	HashMap<String,ArrayList<String>> menu = new HashMap<String,ArrayList<String>>();  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		resta = getIntent().getStringExtra("restaurant");
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		adapter = new MyExpandableListAdapter(this, groups,mAdapter);
		menu.put("Soups", new ArrayList<String>(){{ add("Manchow");add("Tomato");}});
		menu.put("Starter", new ArrayList<String>(){{ add("Tandoori");add("Grilled");}});
		menu.put("Curry", new ArrayList<String>(){{ add("PBM");add("BCM");}});
		Log.d("before", "createdata");
		createData();
		Log.d("after", "createdata");
		ctx=this;
		vp = (ViewPager)findViewById(R.id.pager);

		actionbar = getActionBar();
		// actionbar.setHomeButtonEnabled(false);
		vp.setAdapter(mAdapter);
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionbar.addTab(actionbar.newTab().setText("MENU")
				.setTabListener(this));
		actionbar.addTab(actionbar.newTab().setText("ORDER")
				.setTabListener(this));
		vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				//Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT).show();
				actionbar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		Log.d("after", "actionstuff");
	}

	public void createData() {
		int ct=0;
		for(Entry<String,ArrayList<String>> e : menu.entrySet() )
		{

			Group group = new Group(e.getKey(),"img");
			for(String s : e.getValue())
			{
				group.children.add(s);
			}
			groups.append(ct, group);
			ct++;
		}
	}
	public MyExpandableListAdapter getAdapter()
	{
		return this.adapter;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		//mAdapter.notifyDataSetChanged();
		vp.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mAdapter.notifyDataSetChanged();
	}


} 