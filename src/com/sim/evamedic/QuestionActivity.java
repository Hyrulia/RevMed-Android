package com.sim.evamedic;

import com.sim.managers.QuestionManager;
import com.sim.managers.QuestionTaskManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

public class QuestionActivity extends Activity {

	QuestionManager manager;
	QuestionTaskManager task;
	ProgressBar bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		task = new QuestionTaskManager(this, 100);
		manager = new QuestionManager(getIntent().getIntExtra("specId", 1), this);
		ListView choiceList = (ListView) findViewById(R.id.choicesList);
		bar = (ProgressBar) findViewById(R.id.countDownBar);
		choiceList.setAdapter(manager);
	}
	
	public void updateProgress(int i) {
		bar.setProgress(i);
	}
	
	public void refreshList() {
		Log.i("refresh", "y");
		manager.notifyDataSetChanged();
	}
	
	public void newTask() {
		task = new QuestionTaskManager(this, 100);
	}
	
	public void newTask(int counter) {
		task = new QuestionTaskManager(this, counter);
	}
	
	public void runTask() {
		task.execute();
	}
	
	public void startRevisionActivity(Intent intent) {
		startActivityForResult(intent, 666);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 666 && resultCode == 999) {
			Log.i("result", "yes");
			manager.nextQuestion();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void update(int counter) {
		manager.update(counter);
	}
	
	public void onTaskFinished() {
		manager.onTaskFinished();
	}
	
	@Override
	protected void onStop() {
		task.cancel(true);
		manager.save();
		super.onStop();
	}
	
	@Override
	protected void onStart() {
		manager.load();
		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
