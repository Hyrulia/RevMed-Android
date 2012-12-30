package com.sim.storage;

import com.sim.evamedic.MyApp;
import android.content.SharedPreferences;

public class LocalStorage {

	private static SharedPreferences settings = MyApp.getContext()
			.getSharedPreferences("Pendu", 0);;
		
	public static void setString(String key, String value) {
		  SharedPreferences.Editor prefEditor = settings.edit();  
	      prefEditor.putString(key, value); 
	      prefEditor.commit();
	}
	
	public static String getString(String key, String defaultValue) {
		return settings.getString(key, defaultValue);
	}
	
	public static void setInt(String key, int value) {
		  SharedPreferences.Editor prefEditor = settings.edit();  
	      prefEditor.putInt(key, value); 
	      prefEditor.commit();
	}
	
	public static int getInt(String key, int defaultValue) {
		return settings.getInt(key, defaultValue);
	}
	
	public static void setBoolean(String key, boolean value) {
		  SharedPreferences.Editor prefEditor = settings.edit();  
	      prefEditor.putBoolean(key, value); 
	      prefEditor.commit();
	}
	
	public static boolean getBoolean(String key, boolean defaultValue) {
		return settings.getBoolean(key, defaultValue);
	}
	
	
	public static void remove(String key) {
		SharedPreferences.Editor prefEditor = settings.edit(); 
		prefEditor.remove(key);
		prefEditor.commit();
	}

}
