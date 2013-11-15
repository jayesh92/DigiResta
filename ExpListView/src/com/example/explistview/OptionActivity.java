package com.example.explistview;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class OptionActivity extends Activity {

	Button imageButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		// Show the Up button in the action bar.
		//setupActionBar();
		addListenerOnButton();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	public void addListenerOnButton() {
		 
//		LayoutInflater li=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//	LinearLayout temp=(LinearLayout) li.inflate(R.layout.activity_option, null);
	//	RelativeLayout rl=(RelativeLayout)temp.findViewById(R.id.relativeLayout3);
		imageButton = (Button) findViewById(R.id.button3);
 
		imageButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				Intent sender = getIntent();
				String name=sender.getStringExtra("restaurant");
				//Toast.makeText(OptionActivity.this,
				//		"ImageButton is clicked!" + name, Toast.LENGTH_SHORT).show();
				Intent myintent = new Intent(OptionActivity.this, Cod.class);
				myintent.putExtra("restaurant",name);
				startActivity(myintent);
			}
			 
		});
		imageButton = (Button) findViewById(R.id.button1);
		 
		imageButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				Intent sender = getIntent();
				String name=sender.getStringExtra("restaurant");
				//Toast.makeText(OptionActivity.this,
				//		"ImageButton is clicked!" + name, Toast.LENGTH_SHORT).show();
				Intent myintent = new Intent(OptionActivity.this, MainActivity.class);
				myintent.putExtra("restaurant",name);
				startActivity(myintent);
			}
			 
		});
			
	}
			

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
