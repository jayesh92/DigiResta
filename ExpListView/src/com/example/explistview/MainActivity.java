package com.example.explistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
  // more efficient than HashMap for mapping integers to objects
  SparseArray<Group> groups = new SparseArray<Group>();
  Context ctx;
  HashMap<String,ArrayList<String>> menu = new HashMap<String,ArrayList<String>>();  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    menu.put("Soups", new ArrayList<String>(){{ add("Manchow");add("Tomato");}});
    menu.put("Starter", new ArrayList<String>(){{ add("Tandoori");add("Grilled");}});
    menu.put("Curry", new ArrayList<String>(){{ add("PBM");add("BCM");}});
    createData();
    ctx=this;
    ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
    final MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
        groups);
    
    listView.setGroupIndicator(null);
    listView.setAdapter(adapter);
    Button bt = (Button) findViewById(R.id.order);
    bt.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(ctx,"Order Button Clicked", Toast.LENGTH_LONG).show();	
			Dialog DialogB = new Dialog(ctx,android.R.style.Theme_Holo_Light_Dialog_MinWidth); 
			DialogB.setContentView(R.layout.my_order);
			DialogB.setTitle("MY ORDER");
			TableLayout ll = (TableLayout) DialogB.findViewById(R.id.displayLinear);
			TableRow newRow1 = new TableRow(ctx);
			TextView column11 = new TextView(ctx);
			  TextView column22 = new TextView(ctx);
			  TextView column33 = new TextView(ctx);
			  TextView column44 = new TextView(ctx);
			 column11.setText("ITEM"+"    ");
			 column22.setText("QUANTITY"+"    ");
			 column33.setText("PRICE"+"    ");
			 column44.setText("AMOUNT"+"    ");
			 newRow1.addView(column11);
			  newRow1.addView(column22);
			  newRow1.addView(column33);
			  newRow1.addView(column44);
			  LayoutParams layoutParams1 = new LayoutParams(
					    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

					layoutParams1.setMargins(30, 20, 30, 0);
			  ll.addView(newRow1, layoutParams1);

			 HashMap<String,HashMap<String,Integer>> q_h =adapter.quantity_hashed;
			int sum=0;
			for(HashMap.Entry<String,HashMap<String,Integer>> e: q_h.entrySet())
			  {
				  for(HashMap.Entry<String,Integer> e1: e.getValue().entrySet())
				  {
					  if(e1.getValue()>0)
					  {
						  TableRow newRow = new TableRow(ctx);
						  TextView column1 = new TextView(ctx);
						  TextView column2 = new TextView(ctx);
						  TextView column3 = new TextView(ctx);
						  TextView column4 = new TextView(ctx);
						  column1.setText(e1.getKey()+"    ");
						  column2.setText(Integer.toString(e1.getValue())+"    ");
						  column3.setText(Integer.toString(DataModel.h.get(e1.getKey()))+"    ");
						  column4.setText(Integer.toString(e1.getValue()*DataModel.h.get(e1.getKey()))+"    ");
						  newRow.addView(column1);
						  newRow.addView(column2);
						  newRow.addView(column3);
						  newRow.addView(column4);
						  LayoutParams layoutParams = new LayoutParams(
								    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

								layoutParams.setMargins(30, 20, 30, 0);
						  ll.addView(newRow, layoutParams);
					  }
						
					  sum+=e1.getValue()*DataModel.h.get(e1.getKey());
				  }
			  }
			DialogB.show();
		}
	});
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