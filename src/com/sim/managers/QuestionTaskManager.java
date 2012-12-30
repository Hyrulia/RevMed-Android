package com.sim.managers;

import java.lang.ref.WeakReference;

import com.sim.evamedic.QuestionActivity;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

public class QuestionTaskManager extends AsyncTask<Void, Integer, Boolean> {

	private WeakReference<QuestionActivity> manager;
	private volatile Boolean running = true;
	private volatile int counter;
	
	public QuestionTaskManager(QuestionActivity m, int c) {
		manager = new WeakReference<QuestionActivity>(m);
		counter = c;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		while(!isCancelled()) {
			SystemClock.sleep(500);
			counter--;
			publishProgress(counter);
			Log.i("counter", isCancelled()+""+running);
		}
		return true;
	}
		
	@Override
	protected void onCancelled(Boolean result) {
		Log.i("cancel", "yes2");
		super.onCancelled(result);
	}
	
	@Override
	protected void onCancelled() {
		Log.i("cancel", "yes1");
		super.onCancelled();
	}
	

	@Override
	protected void onProgressUpdate(Integer... values) {
		manager.get().update(values[0]);
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		Log.i("post", "yes");
		if(manager.get() != null)
			manager.get().onTaskFinished();
		super.onPostExecute(result);
	}
}
