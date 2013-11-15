package com.example.explistview;

import android.os.Bundle;

import android.widget.Button;
import android.widget.Spinner;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class LandingActivity extends Activity {

	private Spinner spinner1;
    private Button btnSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		//Intent myintent = new Intent(MainActivity.this, OptionActivity.class);
		//startActivity(myintent);
		addListenerOnButton();
	}

	public void addListenerOnButton() {
		spinner1 = (Spinner) findViewById(R.id.restaurant_spinner);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			  public void onClick(View v) {
				Intent myintent = new Intent(LandingActivity.this, OptionActivity.class);
				myintent.putExtra("restaurant",String.valueOf(spinner1.getSelectedItem()));
				startActivity(myintent);
			  }
		 
			});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
