package com.sim.managers;

import java.util.List;
import com.sim.dao.ScoreDAO;
import com.sim.entities.Score;
import com.sim.pattern.DAOFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ScoreManager extends BaseAdapter {

	private List<Score> scores;
	
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
		
		return null;
	}
	
}
