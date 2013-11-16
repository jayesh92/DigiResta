package com.example.explistview;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter{

	ArrayList<MenuModel> order;
	private Context ctx;
	public Activity act;
	String parent1;
	private LayoutInflater inflater=null;
	MenuModel children;
	ImageView iv;
	HashMap<String,HashMap<String,Integer>> quantity_hashed;
	public OrderAdapter(ArrayList<MenuModel> order, Context ctx,
			HashMap<String,HashMap<String,Integer>> q_h,Activity act) {
		this.order=order;
		this.act=act;
		this.inflater=act.getLayoutInflater();
		this.quantity_hashed=((MainActivity)act).adapter.quantity_hashed;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return order.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return order.get(position);
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.d("getview orderadapter", "in for " + order.get(position).getname() + " " 
				+Integer.toString(order.size()));
		
		if(convertView==null)
			convertView=inflater.inflate(R.layout.order_row, parent,false);
		children = (MenuModel) getItem(position);
		parent1=children.getcategory();
		convertView.setTag(parent1);
		//Log.d("in getchildview", ""+ children.getname());
		TextView text = null;
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
		/*if(tv1.getText().toString().equals("0"))
		{
			return null;
		}*/
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
				bt11.setOnClickListener(new OnClickListener() {

		
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
				n2=n2+1;
				
				iv=(ImageView)v1.findViewById(R.id.selected);
				iv.setVisibility(View.VISIBLE);
				TextView tv1=(TextView)v1.findViewById(R.id.quantity);
				tv1.setText(Integer.toString(n2));
				Log.d("in plus", "For " + par_name + " " + child_name);
				tv1.setVisibility(View.VISIBLE);
				
				HashMap<String,Integer> h = new HashMap<String,Integer>();

				//quantity_hashed.put(parent1,h );
				if(!quantity_hashed.containsKey(par_name))
					quantity_hashed.put(par_name, h);
				quantity_hashed.get(par_name).put(child_name,n2);
				((MainActivity) act).adapter.quantity_hashed = quantity_hashed;
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
					for(MenuModel i: order)
					{
						if(i.getname().equals(child_name))
						{
							order.remove(i);
							break;
						}
					}
					notifyDataSetChanged();
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
				((MainActivity) act).adapter.quantity_hashed = quantity_hashed;
				//mAdapter.notifyDataSetChanged();
			}
		});




		//convertView.setFocusableInTouchMode(true);
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(act,DetailActivity.class);
				intent.putExtra("name", children.getname());
				act.startActivity(intent);

			}

		});


		return convertView;
	}

}
