package com.sim.evamedic;

import com.sim.managers.QuestionManager;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

public class QuestionActivity extends Activity {

	QuestionManager manager;
	ProgressBar bar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		manager = new QuestionManager(getIntent().getIntExtra("objId", 1), this);
		ListView choiceList = (ListView) findViewById(R.id.choicesList);
		final Button validate = (Button) findViewById(R.id.validateButton);
		final Button revision = (Button) findViewById(R.id.revisionButton);
		
		choiceList.setAdapter(manager);
		validate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				manager.validate();
				refreshList();
				validate.setVisibility(View.INVISIBLE);
				revision.setVisibility(View.VISIBLE);
			}
		});
		
		revision.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				manager.ShowRevision();
				validate.setVisibility(View.VISIBLE);
				revision.setVisibility(View.INVISIBLE);
			}
		});
	}
	

	
	public void refreshList() {
		Log.i("refresh", "y");
		manager.notifyDataSetChanged();
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
			
	@Override
	protected void onStop() {
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
