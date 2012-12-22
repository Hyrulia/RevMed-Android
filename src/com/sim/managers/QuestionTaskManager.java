package com.sim.managers;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class QuestionTaskManager extends AsyncTask<Void, Integer, Boolean> {

	WeakReference<QuestionManager> manager;
	int counter;
	
	public QuestionTaskManager(QuestionManager m) {
		manager = new WeakReference<QuestionManager>(m);
	}
	
	@Override
	protected void onPreExecute() {
		counter = 100;
		super.onPreExecute();
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		while(true) {
		try {
			if(counter < 0)
				break;
			
			Thread.sleep(1000);
			counter--;
			manager.get().update(counter);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		}
		return true;
	}
	
	public void setCounter(int c) {
		counter = c;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if(result)
			manager.get().setFinished(true);
			manager.get().refreshListView();
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					manager.get().nextQuestion();
				}
			}, 3000);
			
		Log.i("task", "finished");
		super.onPostExecute(result);
	}
}
