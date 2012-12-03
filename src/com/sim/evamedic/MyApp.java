package com.sim.evamedic;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;


public class MyApp extends Application {

	private Map<String, String> vars = new HashMap<String, String>();

	public Map<String, String> getVars() {
		return vars;
	}
	
	private static Application app; 
	public static Application getInstance(Application x) {
		if (app == null)
			app = x;
		return app;
	}
	
}
