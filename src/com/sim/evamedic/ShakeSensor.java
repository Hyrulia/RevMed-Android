package com.sim.evamedic;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ShakeSensor implements SensorEventListener {

	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	WeakReference<QuestionActivity> activity;
	public ShakeSensor(QuestionActivity activity) {
		this.activity = new WeakReference<QuestionActivity>(activity);
		mSensorManager = (SensorManager) MyApp.getContext()
				.getSystemService(Context.SENSOR_SERVICE);
		
		mAccel = 0.00f;
		mAccelCurrent = SensorManager.GRAVITY_EARTH;
		mAccelLast = SensorManager.GRAVITY_EARTH;
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		

	}


	public void start() {
		mSensorManager.registerListener(this, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
				SensorManager.SENSOR_DELAY_NORMAL);
	}


	public void Pause() {
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		mAccelLast = mAccelCurrent;
		mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
		float delta = mAccelCurrent - mAccelLast;
		mAccel = mAccel * 0.9f + delta; // perform low-cut filter
		if(mAccel > 2) 
			if(activity.get() != null)
				activity.get().onShake();
		Log.i("sha", "y");
	}

}
