package com.sim.dao;

import java.util.ArrayList;
import android.database.Cursor;
import com.sim.entities.Choice;
import com.sim.pattern.DAO;

public class ChoiceDAO extends DAO<Choice> {
	
	public static final String TABLE_NAME   = "choices";
	public static final String ID        	= "_id";
	public static final String CHOICE  		= "choice";
	public static final String QUESTION_ID  = "question_id";
	//ok

	@Override
	public long create(Choice c) {
		return 0;
	}

	@Override
	public Choice getById(int id) {
		
		return null;
	}
	
	@Override
	public ArrayList<Choice> getAll() {
		return null;
	}
	

	public Choice[] getByQuestionId(int id) {

		Cursor crs = db.query(TABLE_NAME, 
			new String[] {ID, CHOICE, QUESTION_ID},
			QUESTION_ID + "=" + id, 
			null, null, null, null);

		Choice[] choices = new Choice[4];

		for(int i = 0; i < 4; i++) {
			crs.moveToNext();
			choices[i] = new Choice();
			choices[i].setId(crs.getInt(crs.getColumnIndex(ID)));
			choices[i].setQuestionId(crs.getInt(crs.getColumnIndex(QUESTION_ID)));
			choices[i].setChoice(crs.getString(crs.getColumnIndex(CHOICE)));
		}
		return choices;
	}

	
}
