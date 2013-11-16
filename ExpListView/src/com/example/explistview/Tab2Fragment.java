package com.example.explistview;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
public class Tab2Fragment extends Fragment{

	int bill2 =0;
	TextView column444;
	TableLayout ll;
	ListView lv;
	HashMap<String,HashMap<String,Integer>> price;
	SparseArray<Group> grps;
	HashMap<String,HashMap<String,Integer>> q_h = new HashMap<String, HashMap<String,Integer>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("tab2","Called");
		int cnt = 0;
		int bill = 0;
		bill2=0;
		Context ctx = getActivity();
		View v =	inflater.inflate(R.layout.tab2, container, false);
		lv = (ListView)v.findViewById(R.id.list_order);
		q_h =((MainActivity)getActivity()).adapter.quantity_hashed;
		grps = ((MainActivity)getActivity()).groups;
		OrderAdapter odapter=new OrderAdapter(make_order(),getActivity(),q_h,getActivity());
		
		
		lv.setAdapter(odapter);
		
		
		/*ll = (TableLayout) v.findViewById(R.id.displayLinear);
		LayoutInflater infla = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for(int i=0;i<10;i++)
		{
		View vi = infla.inflate(R.layout.order_row, null);
		TableRow newRow1 = new TableRow(ctx);
		
		LayoutParams layoutParams1 = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		TextView t = new TextView(ctx);
		t.setText("Chu Chu");
		newRow1.addView(vi);
		newRow1.setLayoutParams(layoutParams1);
		//layoutParams1.setMargins(30, 20, 30, 0);
		ll.addView(newRow1,layoutParams1);
		Log.d("inloop",""+i);
		}*/
		return v;
	}
	private ArrayList<MenuModel> make_order() {
		// TODO Auto-generated method stub
		ArrayList<MenuModel> order = new ArrayList<MenuModel>();
		for(Entry<String,HashMap<String,Integer>> e: q_h.entrySet())
		{
			for(Entry<String,Integer> e1: e.getValue().entrySet())
			{
				Log.d("q_h ", " " + e1.getKey() + " " + e1.getValue());
			}
		}
		int key = 0;
		for(int i = 0; i < grps.size(); i++) {
		   key = grps.keyAt(i);
		   // get the object by the key.
		   Group g = grps.get(key);
		   //price.put(g.string, new HashMap<String,Integer>());
		   for(MenuModel e : g.children)
		   {
			   if(q_h.containsKey(g.string) && q_h.get(g.string).containsKey(e.getname()))
			   {
				   int val=q_h.get(g.string).get(e.getname());
				   if(val > 0)
				   {
					   order.add(e);
					   Log.d("In Order", "Added "+e.getname());
				   }
			   }
		   }
		}
		return order;
	}	
}
