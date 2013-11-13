package com.example.explistview;


import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
public class Tab1Fragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tab1, container, false);
		v.setVerticalScrollBarEnabled(false);
		v.setHorizontalScrollBarEnabled(false);
		ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.listView);
		listView.setVerticalScrollBarEnabled(false);
		listView.setHorizontalScrollBarEnabled(false);
		listView.setGroupIndicator(null);
		listView.setAdapter(((MainActivity)getActivity()).adapter);
		return v;
	}
}
