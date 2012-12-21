package com.sim.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import com.sim.entities.Question;
import com.sim.pattern.DAO;

public class QuestionDAO extends DAO<Question> {

	public final static String TABLE_NAME = "questions";
	public final static String ID = "_id";
	public final static String QUESTION = "question";
	public final static String SPECIALITY_ID = "speciality_id";
	
	@Override
	public long create(Question q) {
		return 0;
	}

	@Override
	public ArrayList<Question> getAll() {
		
		return null;
	}
	
	
	
	@Override
	public Question getById(int id) {
		
		return null;
	}
	
	public List<Question> getLimitedAll(int specialityId, int max) {
		return getBySpecialityId(specialityId).subList(0, max);
	}
	
	public List<Question> getBySpecialityId(int id) {
		Cursor crs = db.query(TABLE_NAME, 
			new String[] { ID, QUESTION, SPECIALITY_ID },
			SPECIALITY_ID + "=" + id, 
			null, null, null, "RANDOM()");

		List<Question> questions = new ArrayList<Question>();
		while(crs.moveToNext()) {
			Question q = new Question();
			q.setId(crs.getInt(crs.getColumnIndex(ID)));
			q.setQuestion(crs.getString(crs.getColumnIndex(QUESTION)));
			q.setSpecialityId(crs.getInt(crs.getColumnIndex(SPECIALITY_ID)));
			questions.add(q);
		}
		crs.close();
		return questions;
	}



	class QuestionTable {
		
	}
}
