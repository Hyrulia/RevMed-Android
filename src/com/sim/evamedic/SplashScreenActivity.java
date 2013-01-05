package com.sim.evamedic;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		MyApp.setContext(getApplicationContext());
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent i = new Intent(SplashScreenActivity.this,
						MenuActivity.class);
				startActivity(i);
				finish();				
			}
		}, 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_splash_screen, menu);
		return true;
	}

}
