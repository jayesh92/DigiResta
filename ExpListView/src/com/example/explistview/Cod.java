package com.example.explistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Cod extends Activity {
	public Spinner myspinner1,myspinner2;
	public String flag1,flag2;
	public String addr;
	ArrayAdapter<CharSequence> adapter1,adapter2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cod);
		spinner();
		addr="";
		Button buttonOne = (Button) findViewById(R.id.button1);
		buttonOne.setOnClickListener(new Button.OnClickListener() {
		 // proceed dabane pe ye func call hota hain
			public void onClick(View v) {
				Intent myintent = new Intent(Cod.this, MainActivity.class); //Intent for new activity
	
		    	addr+=flag1+"$" +flag2+"$";
				//myintent.putExtra("city", flag1);
		    	//myintent.putExtra("locality", flag2);
		    	EditText et1 = (EditText) findViewById(R.id.edittext1);
		    	String street = et1.getText().toString();
		    	EditText et3 = (EditText) findViewById(R.id.edittext3);
		    	String mobile = et3.getText().toString();
		    	
		    	addr+=street+"$"+mobile;
		    	//myintent.putExtra("street", street);
		    	//myintent.putExtra("flat", flat);
		    	//myintent.putExtra("mobile", mobile);   	
		    	myintent.putExtra("address",addr);
		    	   myintent.putExtra("name",
                           "ed1.getText().toString()");
                   myintent.putExtra("table",
                           "ed2.getText().toString()");
                   myintent.putExtra("restaurant","d39");
                
		    	startActivity(myintent); //start activity
		    }
		});
	}

	void spinner (){
		 myspinner1 = (Spinner) findViewById(R.id.spinner1);
		 myspinner2 = (Spinner) findViewById(R.id.spinner2);
		 adapter1 = ArrayAdapter.createFromResource(this,
		        R.array.mycities, android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
		 adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner	
		
		 myspinner1.setAdapter(adapter1);
		Log.d("sad","aaya");		
		 
		
		myspinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			 
	        @Override
	        public void onItemSelected(AdapterView<?> arg0, View arg1,
	                int arg2, long arg3) {
	            // TODO Auto-generated method stub
	        	flag1=myspinner1.getSelectedItem().toString();            
	    
	        	Log.d("trace","flag is" + flag1);
 
	        if(flag1.equals("Hyderabad"))
	   		 {
	   			 		adapter2 = ArrayAdapter.createFromResource(Cod.this,
	   			        R.array.Hyderabad, android.R.layout.simple_spinner_item);
	     }
	   		 else if(flag1.equals("Bangalore"))
	   		 {
	   		 		adapter2 = ArrayAdapter.createFromResource(Cod.this,
	   				        R.array.Bangalore, android.R.layout.simple_spinner_item);
	   		 }
	   		 adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		myspinner2.setAdapter(adapter2);
		    	    
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	            // TODO Auto-generated method stub

	        }

	    });
		myspinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

	        @Override
	        public void onItemSelected(AdapterView<?> arg0, View arg1,
	                int arg2, long arg3) {
	            // TODO Auto-generated method stub
	        	flag2=myspinner2.getSelectedItem().toString();            
	           }

	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	            // TODO Auto-generated method stub

	        }

	    });


	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cod, menu);
		return true;
	}

}
