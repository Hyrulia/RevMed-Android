package com.sim.dao;

import java.util.ArrayList;

import android.database.Cursor;
import com.sim.entities.Score;
import com.sim.pattern.DAO;

public class ScoreDAO extends DAO<Score>{

	public final static String TABLE_NAME = "scores";
	public final static String ID = "_id";
	public final static String SCORE = "score";
	public final static String SPECIALITY_ID = "speciality_id";
	public final static String PSEUDO = "pseudo";
	public final static String DATE = "date";
	
	@Override
	public long create(Score object) {
		
		return 0;
	}

	@Override
	public ArrayList<Score> getAll() {
		
		return null;
	}

	@Override
	public Score getById(int id) {
		
		return null;
	}
	
	public ArrayList<Score> getBySpecialityId(int id) {
		Cursor crs = db.query(TABLE_NAME, 
			new String[] { ID, SCORE, SPECIALITY_ID, PSEUDO, DATE},
			SPECIALITY_ID + "=" + id, 
			null, null, null, SCORE);

		ArrayList<Score> scores = new ArrayList<Score>();
		while(crs.moveToNext()) {
			Score s = new Score();
			s.setId(crs.getInt(crs.getColumnIndex(ID)));
			s.setScore(crs.getInt(crs.getColumnIndex(SCORE)));
			s.setSpecialityId(crs.getInt(crs.getColumnIndex(SPECIALITY_ID)));
			s.setPseudo(crs.getString(crs.getColumnIndex(PSEUDO)));
			s.setDate(crs.getString(crs.getColumnIndex(DATE)));
			scores.add(s);
		}
		crs.close();
		return scores;
	}

}
