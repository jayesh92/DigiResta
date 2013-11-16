package com.example.explistview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class OptionActivity extends Activity {

	Button imageButton;
	EditText ed1,ed2;
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
		ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
		 
		mImageView.setOnClickListener(new View.OnClickListener() {

			  @Override
			  public void onClick(View view) {
			    // do stuff
				  Intent sender = getIntent();
					String name=sender.getStringExtra("restaurant");
					//Toast.makeText(OptionActivity.this,
					//		"ImageButton is clicked!" + name, Toast.LENGTH_SHORT).show();
					Intent myintent = new Intent(OptionActivity.this, Cod.class);
					myintent.putExtra("restaurant",name);
					startActivity(myintent);
			  }

			});
				ImageView mImageView2 = (ImageView) findViewById(R.id.imageView2);
		 
		mImageView2.setOnClickListener(new View.OnClickListener() {

			  @Override
			  public void onClick(View view) {
			    // do stuff
				  Intent sender = getIntent();
					String name=sender.getStringExtra("restaurant");
					//Toast.makeText(OptionActivity.this,
					//		"ImageButton is clicked!" + name, Toast.LENGTH_SHORT).show();
				    AlertDialog.Builder build = new AlertDialog.Builder(OptionActivity.this);
	                View v = OptionActivity.this.getLayoutInflater().inflate(R.layout.dialog_cust_det, null);
	                //build.setTitle("Hii");
	                ed1 = (EditText)v.findViewById(R.id.editcustname);
	                ed2 = (EditText)v.findViewById(R.id.edittable);
	                build.setView(v);
	                build.setPositiveButton("GO",new DialogInterface.OnClickListener() {
	                     
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        // TODO Auto-generated method stub
	                    	String s_name=getIntent().getStringExtra("restaurant");
	                    	Intent s_intent1 = new Intent(OptionActivity.this,MainActivity.class);
	                    	 s_intent1.putExtra("name",
	                                 ed1.getText().toString());
	                         s_intent1.putExtra("table",
	                                 ed2.getText().toString());
	                         s_intent1.putExtra("address", "$notcod$");
	                         s_intent1.putExtra("restaurant",s_name);
	                             Log.d("Jayesh",ed1.getText().toString());
	                        startActivity(s_intent1);
	                         
	                    }
	                });
	                //build.setTitle()
	                AlertDialog d = build.create();
	                d.show();
	            
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