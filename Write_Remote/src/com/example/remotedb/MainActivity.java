package com.example.remotedb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.AsyncTask;
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

	// Async Task to access the web
	private class JsonReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Log.d("Before","doInBackground");
			HttpClient httpclient = new DefaultHttpClient();
			Log.d("params",params[0]);						
			HttpPost httppost = new HttpPost(params[0]);
			try 
			{
				//The Variables Below are Added in form Of Name Value Pairs.(Post Data)
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	            nameValuePairs.add(new BasicNameValuePair("table_no", params[1]));
	            nameValuePairs.add(new BasicNameValuePair("bill_cost", params[2]));
	            nameValuePairs.add(new BasicNameValuePair("cust_name", params[3]));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            //Final Request
				HttpResponse response = httpclient.execute(httppost);
				//The Line Below is BOGUS because it caused app to shut down.
			    jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
				Log.d("jsonResult",jsonResult);
			}
			catch (ClientProtocolException e) 
			{
				Log.d("catch","1");
				e.printStackTrace();
			}
			catch (IOException e)
			{
				Log.d("catch","2");
				e.printStackTrace();
			}
			Log.d("After","doInBackground");
			return null;
		}
        //The StringBuilder Class is Bogus. It cause App to crash if removed.
		private StringBuilder inputStreamToString(InputStream is) {
			StringBuilder answer = new StringBuilder();
			return answer;
		}

	}// end async task

	public void accessWebService() {
		JsonReadTask task = new JsonReadTask();
		// passes values for the urls string array
		Log.d("Before","task.execute");
		//The Sequence Being The URL, The Table Number, Bill and Customer Name in String Format.
		task.execute(new String[] { url,"121","1200","Tumhara Baap" });
		Log.d("After","task.execute");
	}

	// build hash set for list view
}