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
		
	public List<Question> getBySpecialityId(int specId, int limit) {
		Cursor crs = db.query(TABLE_NAME, 
			new String[] { ID, QUESTION, SPECIALITY_ID },
			SPECIALITY_ID + "=" + specId, 
			null, null, null, "RANDOM() LIMIT " + limit);

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

}
