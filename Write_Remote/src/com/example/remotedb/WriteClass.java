package com.example.remotedb;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class WriteClass extends AsyncTask<String, Void, String> {
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
}// end async task