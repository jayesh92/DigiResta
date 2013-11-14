package com.example.location;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 bindComponents();
	        addListener();
	        init();
	    Log.d("create", "activity created");
	}
	 @Override
	    protected void onResume() {
	        IntentFilter filter = new IntentFilter();
	        filter.addAction("com.tutorialspoint.CUSTOM_INTENT");
	        registerReceiver(MyReceiver, filter);
	        super.onResume();
	    }

	    @Override
	    protected void onPause() {
	        unregisterReceiver(MyReceiver);
	        super.onPause();
	    }
	public BroadcastReceiver MyReceiver = new BroadcastReceiver() {

		   @Override
		   public void onReceive(Context context, Intent intent) {
		Log.d("abcd","Intent detected"+intent.getDoubleExtra("Latitude", 0.0)+" "+intent.getDoubleExtra("Longitude", 0.0)+" "+intent.getStringExtra("Provider") );
			   Toast.makeText(context, "Intent detected "+intent.getDoubleExtra("Latitude", 0.0)+" "+intent.getDoubleExtra("Longitude", 0.0)+" "+intent.getStringExtra("Provider") , Toast.LENGTH_LONG).show();
		   }

		};
	private void bindComponents() {
    	btn1 = (Button)findViewById(R.id.btn1);
    	}
    
    private void addListener() {
    	btn1.setOnClickListener(this);
    		}
    
    private void init() {
	
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn1:
			Toast.makeText(this, "Start Button", Toast.LENGTH_SHORT).show();
			Intent mIntent = new Intent();
			mIntent.setClass(this, LocationService.class);
			startService(mIntent);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
