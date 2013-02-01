package com.sim.evamedic;

import org.achartengine.GraphicalView;
import com.sim.chart.ChartScore;
import com.sim.managers.ScoreManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

public class ScoreActivity extends Activity {

	GraphicalView view = null;
	boolean chartLoaded = false;
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
		TextView objective = (TextView) v.findViewById(R.id.itemObjectiveText);
		
		pseudo.setText("Pseudo");
		score.setText("Score");
		date.setText("Date");
		objective.setText("Objective");
		
		list.addHeaderView(v);
		list.setAdapter(manager);
	}
	
	@Override
	protected void onResume() {
		if(!chartLoaded) {
			chartLoaded = true;
			ChartScore chart = new ChartScore();
			LinearLayout layout = (LinearLayout) findViewById(R.id.myLayout); 
			layout.addView(chart.getView(), new TableLayout.LayoutParams
					(TableLayout.LayoutParams.MATCH_PARENT, 0, 1f));
		} 
		    
		super.onResume();
	}

}

