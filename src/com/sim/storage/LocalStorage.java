package com.sim.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {

	public static final String NUMBER = "number";
	public static final String NAME = "name";
	public static final String SOUND = "sound";
	public static final String FIRSTRUN = "firstrun";
	
	private static SharedPreferences settings;
	private static LocalStorage storage;
	
	private LocalStorage(Context context) {
		if(settings == null)
			settings = context.getSharedPreferences("Millionnaire",	0);
	}
	
	public static LocalStorage getInstance(Context context) {
		if(storage == null)
			storage = new LocalStorage(context);
		return storage;
	}

	public void setItem(String key, String value) {
		  SharedPreferences.Editor prefEditor = settings.edit();  
	      prefEditor.putString(key, value); 
	      prefEditor.commit();
	}
	
	public String getItem(String key, String defaultValue) {
		return settings.getString(key, defaultValue);
	}

}
