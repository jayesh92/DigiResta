package com.example.remotedb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	private String jsonResult;
	private String url = "http://10.0.2.2/android_insert.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Before","accessWebService");
		accessWebService();
		Log.d("After","accessWebService");
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void accessWebService() {
		WriteClass task = new WriteClass();
		// passes values for the urls string array
		Log.d("Before","task.execute");
		//The Sequence Being The URL, The Table Number, Bill and Customer Name in String Format.
		task.execute(new String[] { url,"110","1100","Jaeysh" });
		
		//You can Access the Public Varialbes of The task Class by accesing it using task.variablename
		Log.d("After","task.execute");
	}

	// build hash set for list view
}