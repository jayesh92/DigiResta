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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
	// more efficient than HashMap for mapping integers to objects
	SparseArray<Group> groups = new SparseArray<Group>();	
	Context ctx;
	private ViewPager vp;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionbar;
	MyExpandableListAdapter adapter;
	OrderAdapter odapter;
	public String resta,daal_addr,cust_name,table;
	HashMap<String,ArrayList<MenuModel>> menu = new HashMap<String,ArrayList<MenuModel>>();
	public boolean cod_flg;
	//@Jayesh
	
	String jsonResult;
	String url="";
	//String url="http://"+getString(R.string.ip)+"/DigiResta/fetch_menu.php";	
	public ArrayList<MenuModel> mymap =new ArrayList<MenuModel>();
	
	public void accessWebService() {
		JsonReadTask task = new JsonReadTask();
		Log.d("Before","task.execute2");
		Log.d("Ip",url);
		task.execute(new String[] { url });
		Log.d("After","task.execute2");
	}

	private class JsonReadTask extends AsyncTask<String, Void, String> {
		private ProgressDialog progressDialog;
		@Override
		protected void onPreExecute()
		{
			//do initialization of required objects objects here    
			progressDialog = ProgressDialog.show(MainActivity.this, "Wait", "Serving...");
			
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
				nameValuePairs.add(new BasicNameValuePair("resta", resta));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				//Final Request
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
			Log.d("After","doInBackground3");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("Before","PostExecute");
			ListDrawer();
			Log.d("after","PostExecute");
			progressDialog.dismiss();
			mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
			adapter = new MyExpandableListAdapter(MainActivity.this, groups,mAdapter);
			/*menu.put("Soups", new ArrayList<String>(){{ add("Manchow");add("Tomato");}});
			menu.put("Starter", new ArrayList<String>(){{ add("Tandoori");add("Grilled");}});
			menu.put("Curry", new ArrayList<String>(){{ add("PBM");add("BCM");}});
			*/
			Log.d("mymap ki size", ""+mymap.size());
			
			Log.d("before", "createdata");
			createData();
			Log.d("after", "createdata");
			
			vp = (ViewPager)findViewById(R.id.pager);

			actionbar = getActionBar();
			// actionbar.setHomeButtonEnabled(false);
			vp.setAdapter(mAdapter);
			actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			actionbar.addTab(actionbar.newTab().setText("MENU")
					.setTabListener(MainActivity.this));
			actionbar.addTab(actionbar.newTab().setText("ORDER")
					.setTabListener(MainActivity.this));
			vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					// on changing the page
					// make respected tab selected
					//Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT).show();
					actionbar.setSelectedNavigationItem(position);
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}
				@Override
				public void onPageScrollStateChanged(int arg0) {
				}
			});

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
				Toast.makeText(getBaseContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

	}// end async task
	
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
				MenuModel temp=new MenuModel();
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String category = jsonChildNode.optString("category");
				String name = jsonChildNode.optString("name");
				String cost = jsonChildNode.optString("cost");
				String recipe = jsonChildNode.optString("recipe");
				Log.v("category",category);
				Log.v("name",name);
				Log.v("price",cost);
				Log.v("recep0e",recipe);
				temp.setname(name);
				temp.setprice(cost);
				temp.setrecepie(recipe);
				temp.setcategory(category);
				mymap.add(temp);
				
			}
			for(MenuModel m: mymap)
			{
				Log.d("in listdrawer", m.getname() + " " + m.getcategory());
			}
		} 
		catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}
		Log.v("Over", "ListDrawer");
	}

	//@Jayesh

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		url="http://"+getString(R.string.ip)+"/DigiResta/fetch_menu.php";
		Log.d("asasa", url);
		Intent receive = getIntent();
		Log.d("Jayesh","asa " + receive.getStringExtra("address"));
		resta = receive.getStringExtra("restaurant");
		daal_addr=receive.getStringExtra("address");
		cust_name=receive.getStringExtra("name");
		table=receive.getStringExtra("table");
		if(daal_addr.equals("$notcod$"))
			cod_flg=false;
		else
		{
			cod_flg=true;
		}
		Log.d("In MainActivity",resta);
		Log.d("In MainActivity",daal_addr);
		Log.d("In MainActivity",cust_name);
		Log.d("In MainActivity",table);
		
		resta ="d39";
		accessWebService();
		
		
		Log.d("after", "actionstuff");
	}

	public void createData() {
		int ct=0;
		Log.d("sizse of mymap", ""+mymap.size());
		for(MenuModel m: mymap)
		{
			
			Log.d("in createdata", m.getname() + " " + m.getcategory());
			if(menu.containsKey(m.getcategory()))
			{
				menu.get(m.getcategory()).add(m);
			}
			else
			{
				menu.put(m.getcategory(), new ArrayList<MenuModel>());
				menu.get(m.getcategory()).add(m);
			}
				
		}
		for(Entry<String,ArrayList<MenuModel>> e : menu.entrySet() )
		{

			Group group = new Group(e.getKey(),"img");
			for(MenuModel s : e.getValue())
			{
				group.children.add(s);
			}
			groups.append(ct, group);
			ct++;
		}
		
	}
	public void update()
	{
		Log.d("in", "update");
	}
	public MyExpandableListAdapter getAdapter()
	{
		return this.adapter;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		//mAdapter.notifyDataSetChanged();
		vp.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mAdapter.notifyDataSetChanged();
	}


} 