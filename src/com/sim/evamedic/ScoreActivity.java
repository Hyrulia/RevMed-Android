package com.sim.evamedic;

import com.sim.managers.ScoreManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		Button buttonBack = (Button) findViewById(R.id.scoreBackButton);
		TextView pseudo = (TextView) v.findViewById(R.id.itemPseudoText);
		TextView score = (TextView) v.findViewById(R.id.itemScoreText);
		TextView date = (TextView) v.findViewById(R.id.itemDateText);
		
		pseudo.setText("Pseudo");
		score.setText("Score");
		date.setText("Date");
		buttonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ScoreActivity.this,
					MenuActivity.class));
				finish();
			}
		});
		
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

