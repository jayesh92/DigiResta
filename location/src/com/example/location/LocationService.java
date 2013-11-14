package com.example.location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocationService extends Service {
	public static final String BROADCAST_ACTION = "Hello World";
	private static final int TWO_MINUTES = 1000 * 30 * 1;
	public LocationManager locationManager;
	public MyLocationListener listener;
	public Location previousBestLocation = null;

	Intent intent;
	int counter = 0;
	/*Jayesh*/
	public String user_id="abcd";
	private String jsonResult;
	private String url = "http://192.168.0.100/DigiResta/insert_gps.php";


	public void accessWebService(double d1,double d2) {
		JsonWriteTask task = new JsonWriteTask();
		Log.d("Before","task.execute");
		task.execute(new String[] { url,user_id , String.valueOf(d1), String.valueOf(d2)});
		Log.d("After","task.execute");
	}

	private class JsonWriteTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute()
		{
			//do initialization of required objects objects here                
		};

		@Override
		protected String doInBackground(String... params) {
			Log.d("Before","doInBackground");
			HttpClient httpclient = new DefaultHttpClient();
			Log.d("params",params[0]);						
			HttpPost httppost = new HttpPost(params[0]);
			try 
			{
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("x_crd", params[2]));
				nameValuePairs.add(new BasicNameValuePair("y_crd", params[3]));
				nameValuePairs.add(new BasicNameValuePair("boy_id", params[1]));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				Log.d("X",params[2]);
				Log.d("Y",params[3]);
				Log.d("XY",params[1]);
				HttpResponse response = httpclient.execute(httppost);
				/*jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
				Log.d("jsonResult",jsonResult);*/
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

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("Before","PostExecute");

			Log.d("after","PostExecute");
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
	}// end async task

	/*Jayesh*/

	@Override
	public void onCreate() {
		super.onCreate();
		intent = new Intent(BROADCAST_ACTION);
		Log.d("create", "service noeee created");
	}

	@Override
	public void onStart(Intent intent, int startId) {    
		Log.d("create", "service noeee1 started");
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		listener = new MyLocationListener();        
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	@Override
	public void onDestroy() {       
		// handler.removeCallbacks(sendUpdatesToUI);     
		super.onDestroy();
		Log.v("STOP_SERVICE", "DONE");
		locationManager.removeUpdates(listener);        
	}   
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("create","In onstartcommand");
		return super.onStartCommand(intent, flags, startId);

	}
	public static Thread performOnBackgroundThread(final Runnable runnable) {
		final Thread t = new Thread() {
			@Override
			public void run() {
				try {
					runnable.run();
				} finally {

				}
			}
		};
		t.start();
		return t;
	}


	public class MyLocationListener implements LocationListener
	{

		public void onLocationChanged(final Location loc)
		{
			Log.i("**************************************", "Location changed");
			if(isBetterLocation(loc, previousBestLocation)) {
				loc.getLatitude();
				loc.getLongitude();             
				//intent.putExtra("Latitude", loc.getLatitude());
				//intent.putExtra("Longitude", loc.getLongitude());     
				//intent.putExtra("Provider", loc.getProvider());                 
				//intent.setAction("com.tutorialspoint.CUSTOM_INTENT");
				//sendBroadcast(intent);
				user_id="abcd";
				double d1=loc.getLatitude();
				double d2=loc.getLongitude();
				accessWebService(d1,d2);
			}                               
		}

		public void onProviderDisabled(String provider)
		{
			Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
		}


		public void onProviderEnabled(String provider)
		{
			Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
		}


		public void onStatusChanged(String provider, int status, Bundle extras)
		{

		}

	}
}