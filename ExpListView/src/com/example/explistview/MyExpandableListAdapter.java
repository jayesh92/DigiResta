package com.example.explistview;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

  private final SparseArray<Group> groups;
  public LayoutInflater inflater;
  public Activity activity;
   
  public HashMap<String, HashMap<String, Integer>> quantity_hashed= new HashMap<String, HashMap<String, Integer>>();
  public MyExpandableListAdapter(Activity act, SparseArray<Group> groups) {
    activity = act;
    this.groups = groups;
    inflater = act.getLayoutInflater();
    
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
    final String children = (String) getChild(groupPosition, childPosition);
    Group group=(Group)getGroup(groupPosition);
    final String parent1=group.string;
    
    TextView text = null;
    
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.row_child, null);
    }
    text = (TextView) convertView.findViewById(R.id.textView1);
    text.setText(children);
    TextView text2 = (TextView) convertView.findViewById(R.id.textView3);
    int tmp = DataModel.h.get(children);
    String tmp2 = Integer.toString(tmp);
    text2.setText("Price: "+tmp2);
    
    //final int n2;
    Button bt11 = (Button) convertView.findViewById(R.id.increment2);
    
	bt11.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int n2 = 0;
			if(quantity_hashed.containsKey(parent1))
			{
			if(quantity_hashed.get(parent1).containsKey(children))
			{
				n2=quantity_hashed.get(parent1).get(children);
			}
			}
			n2=n2+1;
			HashMap<String,Integer> h = new HashMap<String,Integer>();
			
			//quantity_hashed.put(parent1,h );
			if(!quantity_hashed.containsKey(parent1))
				quantity_hashed.put(parent1, h);
			quantity_hashed.get(parent1).put(children,n2);
			
			
		}
	});

Button bt21 = (Button) convertView.findViewById(R.id.decrement2);
    
	bt21.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int n2 = 0;
			if(quantity_hashed.containsKey(parent1))
			{
			if(quantity_hashed.get(parent1).containsKey(children))
			{
				n2=quantity_hashed.get(parent1).get(children);
			}
			}
			if(n2>=1)
			n2=n2-1;
			HashMap<String,Integer> h = new HashMap<String,Integer>();
			
			//quantity_hashed.put(parent1,h );
			if(!quantity_hashed.containsKey(parent1))
				quantity_hashed.put(parent1, h);
			quantity_hashed.get(parent1).put(children,n2);
			
			
		}
	});


	
	
    //convertView.setFocusableInTouchMode(true);
    convertView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
    	final Dialog dialogA = new Dialog(activity,android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    	dialogA.setContentView(R.layout.quantity);
    	dialogA.setTitle(children);
    	final TextView tv1=(TextView) dialogA.findViewById(R.id.qu);
    	if(quantity_hashed.containsKey(parent1))
    	{
    	if(quantity_hashed.get(parent1).containsKey(children))
    	{
    		tv1.setText(Integer.toString(quantity_hashed.get(parent1).get(children)));
    	}
    	else
    		tv1.setText("0");
    	}
    	else
    		tv1.setText("0");
    	
    	
    	final TextView tv2=(TextView) dialogA.findViewById(R.id.title);
    	tv2.setText(children);
    	
    	Button bt1 = (Button) dialogA.findViewById(R.id.increment);
    	bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String s1=tv1.getText().toString();
				int n=Integer.parseInt(s1);
				n=n+1;
				tv1.setText(Integer.toString(n));
				
			}
		});
    	Button bt2 = (Button) dialogA.findViewById(R.id.decrement);
    	bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String s1=tv1.getText().toString();
				int n=Integer.parseInt(s1);
				if(n>=1)
					n=n-1;
				tv1.setText(Integer.toString(n));
				
			}
		});
    	Button bt3 = (Button) dialogA.findViewById(R.id.buy);
    	bt3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String s1=tv1.getText().toString();
				int n=Integer.parseInt(s1);
				HashMap<String,Integer> h = new HashMap<String,Integer>();
				
				//quantity_hashed.put(parent1,h );
				if(!quantity_hashed.containsKey(parent1))
					quantity_hashed.put(parent1, h);
				quantity_hashed.get(parent1).put(children,n);
				int temp=quantity_hashed.get(parent1).get(children);
				Toast.makeText(activity, Integer.toString(temp), Toast.LENGTH_LONG).show();
				TextView tv= (TextView) activity.findViewById(R.id.bill);
			    tv.setText("BILL: " + Integer.toString(generate_bill(quantity_hashed)));
				dialogA.dismiss();
			}
		});
    	
    	dialogA.show();
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
    ImageView iv1 = (ImageView)convertView.findViewById(R.id.iv1);
    iv1.setImageResource(R.drawable.ic_launcher);
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
		  }
	  }
	  
	  return sum;
  }
} 