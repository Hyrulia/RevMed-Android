package com.sim.evamedic;

import com.sim.entities.Speciality;
import com.sim.managers.SpecialityManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SpecialityActivity extends Activity {

	SpecialityManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speciality);
		MyApp.setContext(getApplicationContext());
		manager = new SpecialityManager();
		manager.fetchSpecialities();
		ListView list = (ListView) findViewById(R.id.specialityList);
		list.setAdapter(manager);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Speciality s = (Speciality) manager.getItem(arg2);
				Intent intent = new Intent(SpecialityActivity.this, 
					QuestionActivity.class);
				intent.putExtra("specId", s.getId());
				startActivity(intent);
				finish();
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
