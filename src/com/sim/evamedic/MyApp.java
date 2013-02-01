package com.sim.evamedic;

import java.lang.reflect.Field;

import android.app.Application;
import android.content.Context;


public class MyApp extends Application {

private static boolean soundEnable = true;
	
	public static boolean isSoundEnable() {
		return soundEnable;
	}
	public static void setSoundEnable(boolean soundEnable) {
		MyApp.soundEnable = soundEnable;
	}
	
	
	private static Context context; 
	
	
	public static void setContext(Context ctx) {
		context = ctx;
	}
	public static Context getContext() {
		return context;
	}
	
	
	public static int getResourceFromString(String variableName, Class<?> c) {

	    try {
	        Field idField = c.getDeclaredField(variableName);
	        return idField.getInt(idField);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1;
	    } 
	}
	
}
