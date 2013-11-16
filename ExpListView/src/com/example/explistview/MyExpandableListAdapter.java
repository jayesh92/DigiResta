package com.example.explistview;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private final SparseArray<Group> groups;
	public LayoutInflater inflater;
	public Activity activity;
	String pp;
	View glo;
	ImageView iv;
	MenuModel children;
	String parent1;
	private TabsPagerAdapter mAdapter;
	HashMap<String, HashMap<String, Integer>> quantity_hashed= new HashMap<String, HashMap<String, Integer>>();
	public MyExpandableListAdapter(Activity act, SparseArray<Group> groups,TabsPagerAdapter mAd) {
		activity = act;
		this.groups = groups;
		inflater = act.getLayoutInflater();
		this.mAdapter=mAd;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).children.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		//final String children = (String) getChild(groupPosition, childPosition);
		children = (MenuModel) getChild(groupPosition,childPosition);
		Log.d("in getchildview", ""+ children.getname());
		Group group=(Group)getGroup(groupPosition);
		parent1=group.string;
		pp = parent1;
		
		TextView text = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_child, null);
		}
		convertView.setTag(parent1);
		text = (TextView) convertView.findViewById(R.id.textView1);
		text.setText(children.getname());
		
		TextView text2 = (TextView) convertView.findViewById(R.id.textView3);
		//int tmp = DataModel.h.get(children);
		Integer tmp = Integer.parseInt(children.getprice());
		String tmp2 = Integer.toString(tmp);
		text2.setText("Price: "+tmp2);
		
		//final int n2;
		iv=(ImageView)convertView.findViewById(R.id.selected);
		TextView tv1 = (TextView)convertView.findViewById(R.id.quantity);
		if(quantity_hashed.containsKey(parent1))
		{
			if(quantity_hashed.get(parent1).containsKey(children.getname()))
			{
				if(quantity_hashed.get(parent1).get(children.getname())>0)
				{
					iv.setVisibility(View.VISIBLE);
					tv1.setText(Integer.toString(quantity_hashed.get(parent1).get(children.getname())));
					tv1.setVisibility(View.VISIBLE);
				}
				else
				{
					iv.setVisibility(View.INVISIBLE);
					tv1.setVisibility(View.INVISIBLE);
				}
			}
			else
			{
				iv.setVisibility(View.INVISIBLE);
				tv1.setVisibility(View.INVISIBLE);
			}
		}
		else
		{
			iv.setVisibility(View.INVISIBLE);
			tv1.setVisibility(View.INVISIBLE);
		}
		ImageButton bt11 = (ImageButton) convertView.findViewById(R.id.increment2);
		glo=convertView;

		bt11.setOnClickListener(new OnClickListener() {

		
			@Override
			public void onClick(View v) {
				int n2 = 0;
				View v1=(View)v.getParent();
				String child_name = ((TextView)v1.findViewById(R.id.textView1))
						.getText().toString();
				String par_name = ((String)((View)v1).getTag());
				
				Log.d("In Increment",""+child_name + " " +par_name);
				if(quantity_hashed.containsKey(par_name))
				{
					if(quantity_hashed.get(par_name).containsKey(child_name))
					{
						n2=quantity_hashed.get(par_name).get(child_name);
					}
				}
				n2=n2+1;
				
				iv=(ImageView)v1.findViewById(R.id.selected);
				iv.setVisibility(View.VISIBLE);
				TextView tv1=(TextView)v1.findViewById(R.id.quantity);
				tv1.setText(Integer.toString(n2));
				Log.d("in plus", "For " + par_name + " " + child_name);
				tv1.setVisibility(View.VISIBLE);
				
				HashMap<String,Integer> h = new HashMap<String,Integer>();
				h.put(child_name, n2);
				//quantity_hashed.put(parent1,h );
				if(!quantity_hashed.containsKey(par_name))
					quantity_hashed.put(par_name, h);
				else
					quantity_hashed.get(par_name).put(child_name,n2);
				
				//mAdapter.notifyDataSetChanged();
			}
		});

		ImageButton bt21 = (ImageButton) convertView.findViewById(R.id.decrement2);
		

		bt21.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int n2 = 0;
				View v1=(View)v.getParent();
				String child_name = ((TextView)v1.findViewById(R.id.textView1))
						.getText().toString();
				String par_name = ((String)((View)v1).getTag());
				
				if(quantity_hashed.containsKey(par_name))
				{
					if(quantity_hashed.get(par_name).containsKey(child_name))
					{
						n2=quantity_hashed.get(par_name).get(child_name);
					}
				}
				if(n2>=1)
					n2=n2-1;

				
				iv=(ImageView)v1.findViewById(R.id.selected);
				if(n2==0)
				{
					iv.setVisibility(View.INVISIBLE);
					TextView tv1=(TextView)v1.findViewById(R.id.quantity);
					tv1.setText(Integer.toString(n2));
					tv1.setVisibility(View.INVISIBLE);
					Log.d("in minus zero", "For " + par_name + " " + child_name);
				}
				else
				{
					TextView tv1=(TextView)v1.findViewById(R.id.quantity);
					tv1.setText(Integer.toString(n2));
					tv1.setVisibility(View.VISIBLE);
					Log.d("in minus", "For " + par_name + " " + child_name);
				}
				HashMap<String,Integer> h = new HashMap<String,Integer>();

				//quantity_hashed.put(parent1,h );
				if(!quantity_hashed.containsKey(par_name))
					quantity_hashed.put(par_name, h);
				quantity_hashed.get(par_name).put(child_name,n2);
				
				//mAdapter.notifyDataSetChanged();
			}
		});




		//convertView.setFocusableInTouchMode(true);
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity,DetailActivity.class);
				intent.putExtra("name", children.getname());
				activity.startActivity(intent);

			}

		});

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).children.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_group, null);
		}
		Group group = (Group) getGroup(groupPosition);
		CheckedTextView ctv=(CheckedTextView) convertView.findViewById(R.id.textView1);
		
		Log.d("In getGroupView", "Group: " + group.string);
		ctv.setText(group.string);
		//ctv.setChecked(isExpanded);
		/*ImageView iv1 = (ImageView)convertView.findViewById(R.id.iv1);
		iv1.setImageResource(R.drawable.ic_launcher);*/
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	public int generate_bill(HashMap<String, HashMap<String , Integer>> q_h)
	{
		int sum=0;
		for(HashMap.Entry<String,HashMap<String,Integer>> e: q_h.entrySet())
		{
			for(HashMap.Entry<String,Integer> e1: e.getValue().entrySet())
			{
				sum+=e1.getValue()*DataModel.h.get(e1.getKey());
				//sum+=e1.getValue()*DataModel.h.get(e1.getKey());
			}
		}

		return sum;
	}

	public int oof(String s1,String s2)
	{
		int n2 = 0;
		if(quantity_hashed.containsKey(s1))
		{
			if(quantity_hashed.get(s1).containsKey(s2))
			{
				n2=quantity_hashed.get(s1).get(s2);
			}
		}
		//	Toast.makeText(activity,Integer.toString(n2), Toast.LENGTH_LONG).show();
		n2=n2+1;

		HashMap<String,Integer> h = new HashMap<String,Integer>();
		if(!quantity_hashed.containsKey(s1))
			quantity_hashed.put(s1, h);
		quantity_hashed.get(s1).put(s2,n2);
		return n2;
	}

	public int oof2(String s1,String s2)
	{
		int n2 = 0;
		if(quantity_hashed.containsKey(s1))
		{
			if(quantity_hashed.get(s1).containsKey(s2))
			{
				n2=quantity_hashed.get(s1).get(s2);
			}
		}
		if(n2>=1)
			n2=n2-1;
		HashMap<String,Integer> h = new HashMap<String,Integer>();

		//quantity_hashed.put(parent1,h );
		if(!quantity_hashed.containsKey(s1))
			quantity_hashed.put(s1, h);
		quantity_hashed.get(s1).put(s2,n2);
		return n2;


	}
	public int billing(String s1,String s2)
	{
		//	  return generate_bill(quantity_hashed);

		int n2=quantity_hashed.get(s1).get(s2);

		return n2*DataModel.h.get(s2);

	}


} 