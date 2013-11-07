package com.example.explistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {
  // more efficient than HashMap for mapping integers to objects
  SparseArray<Group> groups = new SparseArray<Group>();
  HashMap<String,ArrayList<String>> menu = new HashMap<String,ArrayList<String>>();  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    menu.put("Soups", new ArrayList<String>(){{ add("Manchow");add("Tomato");}});
    menu.put("Starter", new ArrayList<String>(){{ add("Tandoori");add("Grilled");}});
    menu.put("Curry", new ArrayList<String>(){{ add("PBM");add("BCM");}});
    createData();
    ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
    MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
        groups);
    
    listView.setGroupIndicator(null);
    listView.setAdapter(adapter);
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

} 