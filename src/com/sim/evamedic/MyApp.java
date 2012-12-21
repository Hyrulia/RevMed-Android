package com.sim.evamedic;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.content.Context;


public class MyApp extends Application {

	private Map<String, String> vars = new HashMap<String, String>();

	public Map<String, String> getVars() {
		return vars;
	}
	
	private static Context context; 
	
	
	public static void setContext(Context ctx) {
		context = ctx;
	}
	public static Context getContext() {
		return context;
	}
	
}
