package com.sim.evamedic;

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
	
}
