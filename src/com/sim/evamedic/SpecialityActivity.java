package com.sim.evamedic;

import com.sim.entities.Objective;
import com.sim.entities.Speciality;
import com.sim.managers.SpecialityManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class SpecialityActivity extends Activity {

	SpecialityManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speciality);
		setTitle("Spécialités et Objectifs");
		MyApp.setContext(getApplicationContext());
		manager = new SpecialityManager();
		manager.fetchSpecialities();
		ExpandableListView list = (ExpandableListView) 
				findViewById(R.id.specialityList);
		list.setAdapter(manager);
		list.setGroupIndicator(null);
		list.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View arg1, int groupPosition,
					int childPostion, long id) {
		
				Objective o = (Objective) manager.getChild(groupPosition, childPostion);
				Speciality s = (Speciality) manager.getGroup(groupPosition);
				final Intent intent = new Intent(SpecialityActivity.this, 
						QuestionActivity.class);
				
				intent.putExtra("objId", o.getId());
				intent.putExtra("objective", o.getObjective());
				intent.putExtra("specId", s.getId());
				intent.putExtra("speciality", s.getSpeciality());
				intent.putExtra("mode", getIntent().getIntExtra("mode", 0));
				
				AlertDialog dialog = new AlertDialog.Builder(SpecialityActivity.this)
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
			return true;
			}
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_specialy, menu);
		return true;
	}

}
