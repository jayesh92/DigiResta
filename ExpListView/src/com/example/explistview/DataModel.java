package com.example.explistview;

import java.util.HashMap;

import android.content.Context;

public class DataModel {
	static Context context;
	public DataModel(Context ctx) {
		context=ctx;

	}
	static HashMap<String,Integer> h = new HashMap<String,Integer>()
			{{
				put("Tandoori", 210);
				put("Grilled", 230);
				put("Manchow", 105);
				put("Tomato", 100);
				put("PBM", 175);
				put("BCM", 225);
			}};

}
