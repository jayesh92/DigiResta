package com.example.remotedb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	private String jsonResult;
	private String url = "http://10.0.2.2/DigiResta/menu.php";
	private ListView listView;
	public ArrayList<String> category1 =new ArrayList<String>();
	public ArrayList<String> category2 =new ArrayList<String>();
	public ArrayList<String> category3 =new ArrayList<String>();
	public ArrayList<String> category4 =new ArrayList<String>();
	public HashMap<String, ArrayList<String>> mymap = new HashMap<String, ArrayList<String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		listView = (ListView) findViewById(R.id.listView1);
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
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
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

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("Before","PostExecute");
			ListDrawer();
			Log.d("after","PostExecute");
		}
	}// end async task

	public void accessWebService() {
		JsonReadTask task = new JsonReadTask();
		// passes values for the urls string array
		Log.d("Before","task.execute");
		task.execute(new String[] { url });
		Log.d("After","task.execute");
	}

	// build hash set for list view
	public void ListDrawer()
	{
		Log.d("Before","ListDrawer");
		try {
			Log.d("Before","1");
			JSONObject jsonResponse = new JSONObject(jsonResult);
			Log.d("Before","2");
			JSONArray jsonMainNode = jsonResponse.optJSONArray("menu");
			Log.d("Before","3");

			for (int i = 0; i < jsonMainNode.length(); i++) 
			{
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String category = jsonChildNode.optString("category");
				String name = jsonChildNode.optString("name");
				Log.v("category",category);
				Log.v("name",name);
				if(category.equals("Starter"))
					category1.add(name);
				else if(category.equals("Gravy"))
					category2.add(name);
				else if(category.equals("Roti"))
					category3.add(name);
				else if(category.equals("Desert"))
					category4.add(name);
			}
		} 
		catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}
		Log.v("Over", "ListDrawer1");
		Log.d("Size",""+category2.size());
		mymap.put("Starter", category1);
		Log.v("Over", "ListDrawer2");
		mymap.put("Gravy", category2);
		Log.v("Over", "ListDrawer3");
		mymap.put("Roti", category3);
		Log.v("Over", "ListDrawer4");
		mymap.put("Desert", category4);
		Log.v("Over", "ListDrawer5");
	}
}