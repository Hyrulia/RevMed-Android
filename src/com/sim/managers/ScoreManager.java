package com.sim.managers;

import java.util.List;
import com.sim.dao.ScoreDAO;
import com.sim.entities.Score;
import com.sim.evamedic.MyApp;
import com.sim.evamedic.R;
import com.sim.pattern.DAOFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScoreManager extends BaseAdapter {

	private List<Score> scores;
	LayoutInflater inflater;
	public ScoreManager() {
		ScoreDAO dao = (ScoreDAO) DAOFactory.create(DAOFactory.SCORE);
		dao.open();
		scores = dao.getAll();
		dao.close();
	}
	
	public static void Save(Score s) {
		ScoreDAO dao = (ScoreDAO) DAOFactory.create(DAOFactory.SCORE);
		dao.open();
		dao.insert(s);
		dao.close();
	}
	
	@Override
	public int getCount() {
		return scores.size();
	}

	@Override
	public Object getItem(int arg0) {
		return scores.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		inflater = (LayoutInflater) MyApp.getContext()
				.getSystemService("layout_inflater");
		View v = inflater.inflate(R.layout.item_score, null);
		TextView pseudo = (TextView) v.findViewById(R.id.itemPseudoText);
		TextView score = (TextView) v.findViewById(R.id.itemScoreText);
		TextView date = (TextView) v.findViewById(R.id.itemDateText);
		
		String scr = String.format("%.2f", scores.get(arg0).getScore());
		pseudo.setText(scores.get(arg0).getPseudo());
		score.setText(scr);
		date.setText(scores.get(arg0).getDate());
		return v;
	}
	
}
