package com.sim.dao;

import java.util.ArrayList;
import java.util.List;

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
	

	public List<Choice> getByQuestionId(int id) {

		Cursor crs = db.query(TABLE_NAME, 
			new String[] {ID, CHOICE, QUESTION_ID},
			QUESTION_ID + "=" + id, 
			null, null, null, "RANDOM()");

		 List<Choice> choices = new ArrayList<Choice>();
		
		while(crs.moveToNext()) {
			Choice choice = new Choice();
			choice.setId(crs.getInt(crs.getColumnIndex(ID)));
			choice.setQuestionId(crs.getInt(crs.getColumnIndex(QUESTION_ID)));
			choice.setChoice(crs.getString(crs.getColumnIndex(CHOICE)));
			choices.add(choice);
		}
		crs.close();
		return choices;
	}

	
}
