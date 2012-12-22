package com.sim.dao;

import android.database.Cursor;
import com.sim.entities.Revision;
import com.sim.pattern.DAO;

public class RevisionDAO extends DAO<Revision> {

	public final static String TABLE_NAME = "revisions";
	public final static String ID = "_id";
	public final static String REVISION = "revision";
	public final static String QUESTION_ID = "question_id";
	
	
	public Revision getByQuestionId(int id) {
		
		Cursor crs = db.query(TABLE_NAME, 
			new String[] {ID, REVISION, QUESTION_ID },
			QUESTION_ID + "=" + id, 
			null, null, null, null);

		crs.moveToNext();
		
		Revision r = new Revision();
		r.setId(crs.getInt(crs.getColumnIndex(ID)));
		r.setQuestionId(crs.getInt(crs.getColumnIndex(QUESTION_ID)));
		r.setRevision(crs.getString(crs.getColumnIndex(REVISION)));
		crs.close();
		return r;
		
	}

}
