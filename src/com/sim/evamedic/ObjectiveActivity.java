package com.sim.evamedic;

import com.sim.entities.Objective;
import com.sim.managers.ObjectiveManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
		setTitle("Objectifs");
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
				final Intent intent = new Intent(ObjectiveActivity.this, 
						QuestionActivity.class);
				intent.putExtra("objId", o.getId());
				intent.putExtra("objective", o.getObjective());
				intent.putExtra("specId", specId);
				intent.putExtra("speciality", speciality);
				
				AlertDialog dialog = new AlertDialog.Builder(ObjectiveActivity.this)
				.setSingleChoiceItems(new String[]{"5 questions", "10 questions"
						, "15 questions"}, 0, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						int nbQuestion = new int[]{5, 10, 15}[which];
						intent.putExtra("nbQuestion", nbQuestion);
					}
					
				})
				.setPositiveButton("Ok", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(intent);	
						finish();
					}
				})
				.setNegativeButton("Cancel", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();						
					}
				})
				.setTitle("Nombre de questions")
				.create();
				dialog.show();
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
