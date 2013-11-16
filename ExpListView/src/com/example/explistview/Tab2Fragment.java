package com.example.explistview;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
public class Tab2Fragment extends Fragment{

	public OrderAdapter odapter;
	//@Jayesh

	String jsonResult;
	String url="";
	//String url="http://"+getString(R.string.ip)+"/DigiResta/submit_dineinorder.php";

	String name="babaji";
	String total_cost;
	String table="12";
	String order_details;

	public void accessWebService() {
		JsonReadTask task = new JsonReadTask();
		Log.d("Before","task.execute2");        
		task.execute(new String[] { url });
		Log.d("After","task.execute2");
	}

	private class JsonReadTask extends AsyncTask<String, Void, String> {
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
				if(((MainActivity)getActivity()).cod_flg==false)
				{
					nameValuePairs.add(new BasicNameValuePair("name", name));
					nameValuePairs.add(new BasicNameValuePair("total_cost", total_cost));
					nameValuePairs.add(new BasicNameValuePair("table", table));
					nameValuePairs.add(new BasicNameValuePair("order_details", order_details));
				}
				else
				{
					nameValuePairs.add(new BasicNameValuePair("total_cost", total_cost));
					nameValuePairs.add(new BasicNameValuePair("order_details", order_details));
					nameValuePairs.add(new BasicNameValuePair("addr",
							((MainActivity)getActivity()).daal_addr));
				}
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				//Final Request
				HttpResponse response = httpclient.execute(httppost);
				/*jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
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
			Log.d("After","doInBackground3");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("Before","PostExecute");
			//ListDrawer();
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
				Toast.makeText(getActivity(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

	}// end async task

	//@Jayesh

	int bill2 =0;
	TextView column444;
	TableLayout ll;
	ListView lv;
	HashMap<String,HashMap<String,Integer>> price;
	SparseArray<Group> grps;
	public HashMap<String,HashMap<String,Integer>> q_h = new HashMap<String, HashMap<String,Integer>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("tab2","Called");
		int cnt = 0;
		int bill = 0;
		if(((MainActivity)getActivity()).cod_flg)
			url="http://"+getString(R.string.ip)+"/DigiResta/submit_codorder.php";
		else
			url="http://"+getString(R.string.ip)+"/DigiResta/submit_dineinorder.php";
		bill2=0;
		Context ctx = getActivity();
		View v =	inflater.inflate(R.layout.tab2, container, false);
		lv = (ListView)v.findViewById(R.id.list_order);
		q_h =((MainActivity)getActivity()).adapter.quantity_hashed;
		grps = ((MainActivity)getActivity()).groups;
		odapter=new OrderAdapter(make_order(),getActivity(),q_h,getActivity());

		lv.setAdapter(odapter);

		Button addGoalButton = (Button) v.findViewById(R.id.proceed);
		addGoalButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				// Pass the fragmentView through to the handler
				// so that findViewById can be used to get a handle on
				// the fragments own views.
				order_details=make_bill();
				accessWebService();
			}
		});
		return v;
	}
	public String make_bill()
	{
		ArrayList<MenuModel>order = odapter.order;
		q_h = odapter.quantity_hashed;
		String order_details="";
		int tc=0;
		int ct=0;
		for(MenuModel m:order)
		{
			int q = q_h.get(m.getcategory()).get(m.getname());
			if(ct==order.size()-1)
				order_details+=m.getname()+","+Integer.toString(q);
			else
				order_details+=m.getname()+","+Integer.toString(q)+",";
			tc=tc+q*Integer.parseInt(m.getprice());
			ct=ct+1;
		}

		Log.d("Bill_Order", order_details);
		total_cost = Integer.toString(tc);
		Log.d("Bill_Cost", total_cost);
		return order_details;
	}
	private ArrayList<MenuModel> make_order() {
		// TODO Auto-generated method stub
		ArrayList<MenuModel> order = new ArrayList<MenuModel>();
		for(Entry<String,HashMap<String,Integer>> e: q_h.entrySet())
		{
			for(Entry<String,Integer> e1: e.getValue().entrySet())
			{
				Log.d("q_h ", " " + e1.getKey() + " " + e1.getValue());
			}
		}
		int key = 0;
		for(int i = 0; i < grps.size(); i++) {
			key = grps.keyAt(i);
			// get the object by the key.
			Group g = grps.get(key);
			//price.put(g.string, new HashMap<String,Integer>());
			for(MenuModel e : g.children)
			{
				if(q_h.containsKey(g.string) && q_h.get(g.string).containsKey(e.getname()))
				{
					int val=q_h.get(g.string).get(e.getname());
					if(val > 0)
					{
						order.add(e);
						Log.d("In Order", "Added "+e.getname());
					}
				}
			}
		}
		return order;
	}	
}
