package com.sim.evamedic;

import com.sim.managers.ScoreManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ScoreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		setTitle("Scores");
		ScoreManager manager = new ScoreManager();
		ListView list = (ListView) findViewById(R.id.scoreList);
		LayoutInflater inflater = (LayoutInflater) MyApp.getContext()
				.getSystemService("layout_inflater");
		View v = inflater.inflate(R.layout.item_score, null);
		TextView pseudo = (TextView) v.findViewById(R.id.itemPseudoText);
		TextView score = (TextView) v.findViewById(R.id.itemScoreText);
		TextView date = (TextView) v.findViewById(R.id.itemDateText);
		
		pseudo.setText("Pseudo");
		score.setText("Score");
		date.setText("Date");
		
		list.addHeaderView(v);
		list.setAdapter(manager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_score, menu);
		return true;
	}

}

