package com.sim.storage;

import android.app.Application;
import com.sim.evamedic.MyApp;

public class SessionStorage {

	
	private static SessionStorage storage;
	private static MyApp global;
	
	private SessionStorage(Application app) {
		if(global == null)
			global = (MyApp) app;
	}
	
	
	public static SessionStorage getInstance(Application app) {
		if(storage == null)
			storage = new SessionStorage(app);
		return storage;
	}
	
	public void setItem(String key, String value) {
		global.getVars().put(key, value);
	}
	
	public String getItem(String key, String defValue) {
		if(global.getVars().containsKey(key))
			return global.getVars().get(key);
		return defValue;
	}

}
