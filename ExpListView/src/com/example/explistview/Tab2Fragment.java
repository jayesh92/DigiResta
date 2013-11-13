package com.example.explistview;
import java.util.HashMap;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
public class Tab2Fragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("tab2","going");
		int cnt = 0;
		int bill = 0;
		int bill2=0;
		Context ctx = getActivity();
		View v =	inflater.inflate(R.layout.tab2, container, false);
		TableLayout ll = (TableLayout) v.findViewById(R.id.displayLinear);
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

		HashMap<String,HashMap<String,Integer>> q_h =((MainActivity)getActivity()).adapter.quantity_hashed;
		int sum=0;

		for(HashMap.Entry<String,HashMap<String,Integer>> e: (q_h.entrySet()))
		{
			for(HashMap.Entry<String,Integer> e1: e.getValue().entrySet())
			{
				final String p = e.getKey();
				if(e1.getValue()>0)
				{
					final String p2 = e1.getKey();
					final int pr1 = DataModel.h.get(e1.getKey());
					final int qu1 = e1.getValue();
					TableRow newRow = new TableRow(ctx);
					final TextView column1 = new TextView(ctx);
					final TextView column2 = new TextView(ctx);
					final TextView column3 = new TextView(ctx);
					final TextView column4 = new TextView(ctx);
					Button plus = new Button(ctx);
					Button minus = new Button(ctx);
					column1.setText(e1.getKey()+"    ");
					column2.setText(Integer.toString(e1.getValue())+"    ");
					column3.setText(Integer.toString(DataModel.h.get(e1.getKey()))+"    ");
					column4.setText(Integer.toString(e1.getValue()*DataModel.h.get(e1.getKey()))+"    ");
					bill2=bill2+e1.getValue()*DataModel.h.get(e1.getKey());
					newRow.addView(column1);
					newRow.addView(column2);
					newRow.addView(column3);
					newRow.addView(column4);
					plus.setId(1);
					minus.setId(2);
					plus.setText("+");
					minus.setText("-");
					plus.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							String tmp_1 = p;
							String tmp_2 = p2;
							int tt = ((MainActivity)getActivity()).adapter.oof(tmp_1,tmp_2);
							column2.setText(Integer.toString(tt)+"    ");
							int amt = tt*pr1;
							//	int tmp=bill2+DataModel.h.get(e1.getKey());
							column4.setText(Integer.toString(amt)+"    ");
						}
					});
					newRow.addView(plus);
					minus.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							String tmp_1 = p;
							String tmp_2 = p2;
							int tt = ((MainActivity)getActivity()).adapter.oof2(tmp_1,tmp_2);
							column2.setText(Integer.toString(tt)+"    ");
							int amt = tt*pr1;
							column4.setText(Integer.toString(amt)+"    ");
							//										bill = ((MainActivity)getActivity()).adapter.billing();
							//	  Toast.makeText(ctx,Integer.toString(bill), Toast.LENGTH_LONG).show();
						}
					});
					newRow.addView(minus);
					LayoutParams layoutParams = new LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

					layoutParams.setMargins(30, 20, 30, 0);
					ll.addView(newRow, layoutParams);
					String tmp11 = p;
					String tmp22 = p2;
					bill2=bill2+((MainActivity)getActivity()).adapter.billing(tmp11, tmp22); 
				}
				sum+=e1.getValue()*DataModel.h.get(e1.getKey());
			}
		}
		TableRow newRow2 = new TableRow(ctx);
		final TextView column111 = new TextView(ctx);
		final TextView column222 = new TextView(ctx);
		final TextView column333 = new TextView(ctx);
		final TextView column444 = new TextView(ctx);
		column111.setText("");
		column222.setText("");
		column333.setText("Current Bill:");
		//	  bill = ((MainActivity)getActivity()).adapter.billing();
		column444.setText(Integer.toString(bill2));
		newRow2.addView(column111);
		newRow2.addView(column222);
		newRow2.addView(column333);
		newRow2.addView(column444);
		LayoutParams layoutParams = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		layoutParams.setMargins(30, 20, 30, 0);
		ll.addView(newRow2, layoutParams);
		return v;
	}

}
