package com.sim.dao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.database.Cursor;
import com.sim.entities.Choice;
import com.sim.pattern.DAO;

public class ChoiceDAO extends DAO<Choice> {

	@Override
	public ArrayList<Choice> getAll() {
		ArrayList<Choice> choices = new ArrayList<Choice>();

		Cursor crs = db.query(ChoiceTable.TABLE_NAME, 
				new String[] { 
				ChoiceTable.ID, 
				ChoiceTable.CHOICE,
				ChoiceTable.QUESTION_ID },
				null, null, null, null, "RANDOM()");

		while(crs.moveToNext()) {
			Choice q = new Choice();
			q.setQuestionId(crs.getInt(crs.getColumnIndex(ChoiceTable.QUESTION_ID)));
			q.setId(crs.getInt(crs.getColumnIndex(ChoiceTable.ID)));
			q.setChoice(crs.getString(crs.getColumnIndex(ChoiceTable.CHOICE)));
			choices.add(q);
		};
		return choices;
	}
	

	public Choice[] getByQuestionId(int id) {

		Cursor crs = db.query(ChoiceTable.TABLE_NAME, 
				new String[] { 
				ChoiceTable.ID, 
				ChoiceTable.CHOICE,
				ChoiceTable.QUESTION_ID },
				ChoiceTable.QUESTION_ID + "=" + id, 
				null, null, null, "RANDOM()");

		Choice[] choices = new Choice[4];

		for(int i = 0; i < 4; i++) {
			crs.moveToNext();
			choices[i] = new Choice();
			choices[i].setId(crs.getInt(crs.getColumnIndex(ChoiceTable.ID)));
			choices[i].setQuestionId(crs.getInt(
					crs.getColumnIndex(ChoiceTable.QUESTION_ID)));
			choices[i].setChoice(crs.getString(crs.getColumnIndex(ChoiceTable
					.CHOICE)));
		}
		return choices;

	}

	@Override
	public long create(Choice c) {
		ContentValues data = new ContentValues();		
		data.put(ChoiceTable.CHOICE, c.getChoice());
		data.put(ChoiceTable.QUESTION_ID, c.getQuestionId());
		return db.insert(ChoiceTable.TABLE_NAME, null, data);
	}

	@Override
	public Choice getById(int id) {
		
		return null;
	}
	
	class ChoiceTable {
		public static final String TABLE_NAME   = "choices";
		public static final String ID        	= "_id";
		public static final String CHOICE  		= "choice";
		public static final String QUESTION_ID  = "question_id";
	}
	
}
