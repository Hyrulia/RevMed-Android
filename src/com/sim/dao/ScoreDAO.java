package com.sim.dao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import com.sim.entities.Score;
import com.sim.pattern.DAO;

public class ScoreDAO extends DAO<Score>{

	public final static String TABLE_NAME = "scores";
	public final static String ID = "_id";
	public final static String SCORE = "score";
	public final static String PSEUDO = "pseudo";
	public final static String DATE = "date";

	public long insert(Score s) {
		ContentValues data = new ContentValues();	
		s.setDate("");
		data.put(SCORE, s.getScore());
		data.put(PSEUDO, s.getPseudo());
		return db.insert(TABLE_NAME, null, data);
	}
	
	public ArrayList<Score> getAll() {
		Cursor crs = db.query(TABLE_NAME, 
			new String[] { ID, SCORE, PSEUDO, DATE},
			null, 
			null, null, null, DATE);

		ArrayList<Score> scores = new ArrayList<Score>();
		while(crs.moveToNext()) {
			Score s = new Score();
			s.setId(crs.getInt(crs.getColumnIndex(ID)));
			s.setScore(crs.getFloat(crs.getColumnIndex(SCORE)));
			s.setPseudo(crs.getString(crs.getColumnIndex(PSEUDO)));
			s.setDate(crs.getString(crs.getColumnIndex(DATE)));
			scores.add(s);
		}
		crs.close();
		return scores;
	}

}
