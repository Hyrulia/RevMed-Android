package com.sim.evamedic;

import com.sim.entities.Objective;
import com.sim.managers.ObjectiveManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ObjectiveActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_objective);
		
		final int specId = getIntent().getIntExtra("specId", 1);
		final String speciality = getIntent().getStringExtra("speciality");
		
		TextView specialityText = (TextView) findViewById(R.id.specialityText);
		ListView objectiveList = (ListView) findViewById(R.id.objectiveList);
		
		final ObjectiveManager manager = new ObjectiveManager(specId);
		specialityText.setText(speciality);
		objectiveList.setAdapter(manager);
		
		objectiveList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Objective o = (Objective) manager.getItem(arg2);
				Intent intent = new Intent(ObjectiveActivity.this, 
						QuestionActivity.class);
				intent.putExtra("objId", o.getId());
				intent.putExtra("objective", o.getObjective());
				intent.putExtra("specId", specId);
				intent.putExtra("speciality", speciality);
				
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_objective, menu);
		return true;
	}

}
