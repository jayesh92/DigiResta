package com.example.explistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MyNewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_new);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent myintent=getIntent();
		//ye jo data log kiya hua hain wo strings bana ke use kiya ja sakta hain directly
		//eg String string1=myintent.getStringExtra("city");
 		Log.d("intent","data"+myintent.getStringExtra("city"));
		Log.d("intent","data"+myintent.getStringExtra("locality"));
		Log.d("intent","data"+myintent.getStringExtra("street"));
		Log.d("intent","data"+myintent.getStringExtra("flat"));
		Log.d("intent","data"+myintent.getStringExtra("mobile"));
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_new, menu);
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
